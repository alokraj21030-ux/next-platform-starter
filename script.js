const STORAGE_KEY = "studentManagementRecords_v1";
const $ = (id) => document.getElementById(id);
let students = loadStudents();
let toastTimer;

function loadStudents() {
  try {
    const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) || "[]");
    return Array.isArray(saved) ? saved : [];
  } catch { return []; }
}
function persist() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(students));
}
function escapeHTML(value) {
  return String(value ?? "").replace(/[&<>"']/g, ch => ({
    "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;", "'":"&#39;"
  })[ch]);
}
function showToast(message) {
  const toast = $("toast");
  toast.textContent = message;
  toast.classList.add("show");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.remove("show"), 2800);
}
function showPage(page) {
  document.querySelectorAll(".page").forEach(el => el.classList.toggle("active", el.id === `page-${page}`));
  document.querySelectorAll(".nav-link").forEach(el => el.classList.toggle("active", el.dataset.page === page));
  if (page === "students" || page === "dashboard") renderStudents();
  if (page === "search") $("searchInput").focus();
  window.scrollTo({top: 0, behavior: "smooth"});
}
document.querySelectorAll(".nav-link").forEach(btn => btn.addEventListener("click", () => {
  const page = btn.dataset.page;
  if (page === "students") showPage("dashboard");
  else if (page === "export") showPage("export");
  else showPage(page);
}));
document.querySelectorAll("[data-go]").forEach(btn => btn.addEventListener("click", () => {
  clearForm();
  showPage(btn.dataset.go);
}));
$("today").textContent = new Date().toLocaleDateString(undefined, {weekday:"short", month:"short", day:"numeric", year:"numeric"});

function updateStats() {
  $("totalCount").textContent = students.length;
  $("courseCount").textContent = new Set(students.map(s => s.course.trim().toLowerCase()).filter(Boolean)).size;
  const avg = students.length ? students.reduce((sum, s) => sum + Number(s.marks), 0) / students.length : 0;
  $("averageMarks").textContent = avg.toFixed(1);
}
function renderStudents(filter = $("dashboardSearch").value.trim().toLowerCase()) {
  const rows = $("studentRows");
  const filtered = students.filter(s => [
    s.rollNo, s.name, s.address, s.email, s.phoneNo, s.course, s.marks
  ].some(value => String(value).toLowerCase().includes(filter)));
  rows.innerHTML = filtered.map(s => `
    <tr>
      <td><strong>${escapeHTML(s.rollNo)}</strong></td>
      <td><div class="student-cell"><div class="avatar">${escapeHTML(initials(s.name))}</div><div><div class="student-name">${escapeHTML(s.name)}</div><div class="student-email">${escapeHTML(s.email)}</div></div></div></td>
      <td>${escapeHTML(s.address)}</td><td>${escapeHTML(s.email)}</td><td>${escapeHTML(s.phoneNo)}</td>
      <td>${escapeHTML(s.course)}</td><td><span class="marks-pill">${Number(s.marks).toFixed(2)}</span></td>
      <td><div class="actions"><button class="icon-btn edit-btn" title="Edit student" aria-label="Edit ${escapeHTML(s.name)}" data-edit="${escapeHTML(s.rollNo)}">✎</button><button class="icon-btn delete-btn" title="Delete student" aria-label="Delete ${escapeHTML(s.name)}" data-delete="${escapeHTML(s.rollNo)}">⌫</button></div></td>
    </tr>`).join("");
  $("emptyState").classList.toggle("show", filtered.length === 0);
  $("resultCount").textContent = `${filtered.length} record${filtered.length === 1 ? "" : "s"}`;
  $("showingText").textContent = `Showing ${filtered.length} of ${students.length} students`;
  updateStats();
}
function initials(name) {
  return String(name).trim().split(/\s+/).slice(0,2).map(part => part[0] || "").join("").toUpperCase() || "S";
}
$("dashboardSearch").addEventListener("input", () => renderStudents());
function clearForm() {
  $("studentForm").reset();
  $("originalRoll").value = "";
  $("formTitle").textContent = "Add New Student";
  $("submitBtn").innerHTML = 'Save Student <span>→</span>';
  $("rollNo").disabled = false;
}
$("resetBtn").addEventListener("click", () => setTimeout(clearForm, 0));

$("studentForm").addEventListener("submit", event => {
  event.preventDefault();
  const originalRoll = $("originalRoll").value;
  const rollNo = Number($("rollNo").value);
  const marks = Number($("marks").value);
  const student = {
    rollNo,
    name: $("name").value.trim(),
    address: $("address").value.trim(),
    email: $("email").value.trim(),
    phoneNo: $("phoneNo").value.trim(),
    course: $("course").value.trim(),
    marks
  };
  if (!Number.isSafeInteger(rollNo) || rollNo < 1) return showToast("Roll number must be a positive whole number.");
  if (!student.name || !student.address || !student.email || !student.phoneNo || !student.course) return showToast("Please complete all fields.");
  if (!Number.isFinite(marks) || marks < 0 || marks > 100) return showToast("Marks must be between 0 and 100.");
  const duplicate = students.some(s => Number(s.rollNo) === rollNo && String(s.rollNo) !== originalRoll);
  if (duplicate) return showToast("That roll number already exists.");
  if (originalRoll) {
    const index = students.findIndex(s => String(s.rollNo) === originalRoll);
    if (index < 0) return showToast("Student record could not be found.");
    students[index] = student;
    showToast("Student updated successfully!");
  } else {
    students.push(student);
    showToast("Student added successfully!");
  }
  persist();
  clearForm();
  renderStudents();
  showPage("dashboard");
});
function editStudent(rollNo) {
  const s = students.find(item => String(item.rollNo) === String(rollNo));
  if (!s) return showToast("Student record not found.");
  $("originalRoll").value = String(s.rollNo);
  $("rollNo").value = s.rollNo;
  $("rollNo").disabled = true;
  $("name").value = s.name;
  $("address").value = s.address;
  $("email").value = s.email;
  $("phoneNo").value = s.phoneNo;
  $("course").value = s.course;
  $("marks").value = s.marks;
  $("formTitle").textContent = `Update Student #${s.rollNo}`;
  $("submitBtn").innerHTML = 'Update Student <span>→</span>';
  showPage("add");
}
function deleteStudent(rollNo) {
  const s = students.find(item => String(item.rollNo) === String(rollNo));
  if (!s) return showToast("Student record not found.");
  if (!confirm(`Delete ${s.name} (Roll No. ${s.rollNo})? This cannot be undone.`)) return;
  students = students.filter(item => String(item.rollNo) !== String(rollNo));
  persist();
  renderStudents();
  renderSearchResults($("searchInput").value.trim());
  showToast("Student deleted successfully.");
}
document.addEventListener("click", event => {
  const edit = event.target.closest("[data-edit]");
  const del = event.target.closest("[data-delete]");
  if (edit) editStudent(edit.dataset.edit);
  if (del) deleteStudent(del.dataset.delete);
});

function searchStudents(query) {
  const q = query.trim().toLowerCase();
  if (!q) return [];
  return students.filter(s => [s.rollNo,s.name,s.address,s.email,s.phoneNo,s.course,s.marks]
    .some(value => String(value).toLowerCase().includes(q)));
}
function renderSearchResults(query) {
  const results = $("searchResults");
  if (!query.trim()) {
    results.innerHTML = '<p class="muted">Enter a search term to find a student.</p>';
    return;
  }
  const found = searchStudents(query);
  if (!found.length) {
    results.innerHTML = '<div class="empty-state show"><div>🔎</div><h3>No matching students</h3><p>Try a different name, roll number, email, or course.</p></div>';
    return;
  }
  results.innerHTML = found.map(s => `<article class="result-card">
    <div><h3>${escapeHTML(s.name)} <span class="muted">#${escapeHTML(s.rollNo)}</span></h3>
    <p>${escapeHTML(s.course)} · ${Number(s.marks).toFixed(2)} marks</p>
    <p>${escapeHTML(s.email)} · ${escapeHTML(s.phoneNo)}</p><p>${escapeHTML(s.address)}</p></div>
    <div class="result-actions"><button class="btn btn-quiet" data-edit="${escapeHTML(s.rollNo)}">Edit</button><button class="btn btn-quiet" data-delete="${escapeHTML(s.rollNo)}">Delete</button></div>
  </article>`).join("");
}
$("searchBtn").addEventListener("click", () => renderSearchResults($("searchInput").value));
$("searchInput").addEventListener("input", () => renderSearchResults($("searchInput").value));
$("searchInput").addEventListener("keydown", e => { if (e.key === "Enter") renderSearchResults(e.target.value); });

function exportCSV() {
  if (!students.length) return showToast("No student records to export yet.");
  const headers = ["Roll No", "Name", "Address", "Email", "Phone No", "Course", "Marks"];
  const quote = value => `"${String(value ?? "").replace(/"/g, '""')}"`;
  const lines = [headers, ...students.map(s => [s.rollNo,s.name,s.address,s.email,s.phoneNo,s.course,Number(s.marks).toFixed(2)])]
    .map(row => row.map(quote).join(","));
  const blob = new Blob(["\uFEFF" + lines.join("\r\n")], {type:"text/csv;charset=utf-8;"});
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = "students.csv";
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
  showToast("CSV file downloaded successfully!");
}
$("exportBtn").addEventListener("click", exportCSV);
renderStudents();
