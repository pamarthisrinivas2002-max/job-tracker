// Base URL of your REST API (same server, so no "http://localhost:8080" needed)
const API = '/api/applications';

// Grab the page elements we need
const form = document.getElementById('app-form');
const formTitle = document.getElementById('form-title');
const submitBtn = document.getElementById('submit-btn');
const cancelBtn = document.getElementById('cancel-btn');
const formError = document.getElementById('form-error');
const tableBody = document.getElementById('app-table');
const emptyMsg = document.getElementById('empty');
const countEl = document.getElementById('count');
const filter = document.getElementById('filter');

const fields = {
    id: document.getElementById('app-id'),
    company: document.getElementById('company'),
    role: document.getElementById('role'),
    location: document.getElementById('location'),
    status: document.getElementById('status'),
    appliedDate: document.getElementById('appliedDate'),
    requiresClearance: document.getElementById('requiresClearance'),
    notes: document.getElementById('notes')
};

// ---------- READ: load and show all applications ----------
async function loadApplications() {
    const status = filter.value;
    const url = status ? `${API}?status=${status}` : API;

    const response = await fetch(url);          // GET request
    const applications = await response.json(); // convert JSON to JS objects
    renderTable(applications);
}

function renderTable(applications) {
    tableBody.innerHTML = '';
    countEl.textContent = `(${applications.length})`;
    emptyMsg.classList.toggle('hidden', applications.length > 0);

    applications.forEach(app => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${escapeHtml(app.company)}</td>
            <td>${escapeHtml(app.role)}</td>
            <td>${escapeHtml(app.location || '-')}</td>
            <td><span class="badge ${app.status.toLowerCase()}">${app.status}</span></td>
            <td>${app.appliedDate || '-'}</td>
            <td>${app.requiresClearance ? 'Yes' : 'No'}</td>
            <td class="row-actions">
                <button class="small" data-edit="${app.id}">Edit</button>
                <button class="small danger" data-delete="${app.id}">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// ---------- CREATE / UPDATE: submit the form ----------
form.addEventListener('submit', async (event) => {
    event.preventDefault(); // stop the browser reloading the page
    formError.textContent = '';

    const data = {
        company: fields.company.value.trim(),
        role: fields.role.value.trim(),
        location: fields.location.value.trim(),
        status: fields.status.value,
        requiresClearance: fields.requiresClearance.checked,
        notes: fields.notes.value.trim()
    };
    if (fields.appliedDate.value) {
        data.appliedDate = fields.appliedDate.value; // only send if chosen
    }

    const id = fields.id.value;
    const response = await fetch(id ? `${API}/${id}` : API, {
        method: id ? 'PUT' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        showError(await response.json());
        return;
    }

    resetForm();
    loadApplications();
});

// Show the error messages from your GlobalExceptionHandler
function showError(error) {
    if (error.errors) {
        formError.textContent = Object.values(error.errors).join('. ');
    } else {
        formError.textContent = error.message || 'Something went wrong';
    }
}

// ---------- EDIT and DELETE buttons in the table ----------
tableBody.addEventListener('click', async (event) => {
    const editId = event.target.dataset.edit;
    const deleteId = event.target.dataset.delete;

    if (editId) {
        const response = await fetch(`${API}/${editId}`); // GET one
        const app = await response.json();
        fillForm(app);
    }

    if (deleteId && confirm('Delete this application?')) {
        await fetch(`${API}/${deleteId}`, { method: 'DELETE' });
        if (fields.id.value === deleteId) resetForm();
        loadApplications();
    }
});

function fillForm(app) {
    fields.id.value = app.id;
    fields.company.value = app.company;
    fields.role.value = app.role;
    fields.location.value = app.location || '';
    fields.status.value = app.status;
    fields.appliedDate.value = app.appliedDate || '';
    fields.requiresClearance.checked = app.requiresClearance;
    fields.notes.value = app.notes || '';

    formTitle.textContent = `Edit application #${app.id}`;
    submitBtn.textContent = 'Save changes';
    cancelBtn.classList.remove('hidden');
    formError.textContent = '';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function resetForm() {
    form.reset();
    fields.id.value = '';
    formTitle.textContent = 'Add application';
    submitBtn.textContent = 'Add application';
    cancelBtn.classList.add('hidden');
    formError.textContent = '';
}

cancelBtn.addEventListener('click', resetForm);
filter.addEventListener('change', loadApplications);

// Prevents HTML injection if someone types <script> into a field
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Load the list when the page opens
loadApplications();