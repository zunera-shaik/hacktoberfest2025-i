let contributions = [];
let currentFilter = "all";
let editingId = null;

// Load data from memory
function loadContributions() {
  renderContributions();
  updateStats();
}

// Add new contribution
document
  .getElementById("contributionForm")
  .addEventListener("submit", function (e) {
    e.preventDefault();

    const contribution = {
      id: Date.now(),
      repo: document.getElementById("repoName").value.trim(),
      prNumber: document.getElementById("prNumber").value.trim(),
      prUrl: document.getElementById("prUrl").value.trim(),
      notes: document.getElementById("notes").value.trim(),
      language: document.getElementById("language").value,
      type: document.getElementById("contributionType").value,
      status: "pending",
      date: new Date().toISOString(),
      dateFormatted: new Date().toLocaleDateString(),
    };

    contributions.push(contribution);
    this.reset();
    renderContributions();
    updateStats();
  });

// Render contributions
function renderContributions() {
  const container = document.getElementById("contributionsList");
  const searchTerm = document.getElementById("searchBox").value.toLowerCase();

  let filtered = contributions.filter((c) => {
    const matchesFilter = currentFilter === "all" || c.status === currentFilter;
    const matchesSearch =
      !searchTerm ||
      c.repo.toLowerCase().includes(searchTerm) ||
      c.prNumber.includes(searchTerm) ||
      (c.notes && c.notes.toLowerCase().includes(searchTerm)) ||
      (c.language && c.language.toLowerCase().includes(searchTerm));
    return matchesFilter && matchesSearch;
  });

  if (filtered.length === 0) {
    container.innerHTML = `
                    <div class="empty-state">
                        <div style="font-size: 4rem;">📝</div>
                        <h3>No contributions yet</h3>
                        <p>Start adding your Hacktoberfest PRs!</p>
                    </div>
                `;
    return;
  }

  container.innerHTML = filtered
    .map(
      (c) => `
                <div class="contribution-item">
                    <div class="contribution-header">
                        <div class="contribution-info">
                            <div class="contribution-repo">🔗 ${c.repo} #${
        c.prNumber
      }</div>
                            <div class="contribution-meta">
                                <span>📅 ${c.dateFormatted}</span>
                                ${
                                  c.language
                                    ? `<span>💻 ${c.language}</span>`
                                    : ""
                                }
                                ${c.type ? `<span>🏷️ ${c.type}</span>` : ""}
                                ${
                                  c.prUrl
                                    ? `<a href="${c.prUrl}" target="_blank" style="color: #EC4899;">🔗 View PR</a>`
                                    : ""
                                }
                            </div>
                            ${
                              c.notes
                                ? `<div class="contribution-notes">📝 ${c.notes}</div>`
                                : ""
                            }
                        </div>
                        <div class="contribution-actions">
                            <span class="status-badge status-${
                              c.status
                            }">${c.status.toUpperCase()}</span>
                            <button class="action-btn edit-btn" onclick="openEditModal(${
                              c.id
                            })">✏️ Edit</button>
                            <button class="action-btn delete-btn" onclick="deleteContribution(${
                              c.id
                            })">🗑️</button>
                        </div>
                    </div>
                </div>
            `
    )
    .join("");
}

// Update statistics
function updateStats() {
  const total = contributions.length;
  const merged = contributions.filter((c) => c.status === "merged").length;
  const approved = contributions.filter((c) => c.status === "approved").length;
  const pending = contributions.filter((c) => c.status === "pending").length;

  document.getElementById("totalContributions").textContent = total;
  document.getElementById("mergedCount").textContent = merged;
  document.getElementById("approvedCount").textContent = approved;
  document.getElementById("pendingCount").textContent = pending;

  const progress = Math.min((merged / 4) * 100, 100);
  document.getElementById("progressFill").style.width = progress + "%";
  document.getElementById("progressPercentage").textContent =
    Math.round(progress) + "%";

  const celebrationDiv = document.getElementById("celebrationMessage");
  if (merged >= 4) {
    celebrationDiv.innerHTML = `
                    <div class="celebration">
                        🎉 Congratulations! You've completed Hacktoberfest 2025! 🎉
                        <br>You've made ${merged} merged contributions to open source!
                    </div>
                `;
  } else {
    celebrationDiv.innerHTML = "";
  }
}

// Filter contributions
function filterContributions(filter) {
  currentFilter = filter;
  document
    .querySelectorAll(".filter-btn")
    .forEach((btn) => btn.classList.remove("active"));
  event.target.classList.add("active");
  renderContributions();
}

// Search functionality
document
  .getElementById("searchBox")
  .addEventListener("input", renderContributions);

// Delete contribution
function deleteContribution(id) {
  if (confirm("Are you sure you want to delete this contribution?")) {
    contributions = contributions.filter((c) => c.id !== id);
    renderContributions();
    updateStats();
  }
}

// Edit modal functions
function openEditModal(id) {
  const contrib = contributions.find((c) => c.id === id);
  if (!contrib) return;

  editingId = id;
  document.getElementById("editId").value = id;
  document.getElementById("editRepo").value = contrib.repo;
  document.getElementById("editPR").value = contrib.prNumber;
  document.getElementById("editUrl").value = contrib.prUrl || "";
  document.getElementById("editNotes").value = contrib.notes || "";
  document.getElementById("editStatus").value = contrib.status;
  document.getElementById("editLanguage").value = contrib.language || "";
  document.getElementById("editType").value = contrib.type || "Bug Fix";

  document.getElementById("editModal").classList.add("active");
}

function closeEditModal() {
  document.getElementById("editModal").classList.remove("active");
  editingId = null;
}

document.getElementById("editForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const id = parseInt(document.getElementById("editId").value);
  const index = contributions.findIndex((c) => c.id === id);

  if (index !== -1) {
    contributions[index] = {
      ...contributions[index],
      repo: document.getElementById("editRepo").value.trim(),
      prNumber: document.getElementById("editPR").value.trim(),
      prUrl: document.getElementById("editUrl").value.trim(),
      notes: document.getElementById("editNotes").value.trim(),
      status: document.getElementById("editStatus").value,
      language: document.getElementById("editLanguage").value,
      type: document.getElementById("editType").value,
    };

    renderContributions();
    updateStats();
    closeEditModal();
  }
});

// Export functions
function exportToJSON() {
  const dataStr = JSON.stringify(contributions, null, 2);
  const dataBlob = new Blob([dataStr], { type: "application/json" });
  const url = URL.createObjectURL(dataBlob);
  const link = document.createElement("a");
  link.href = url;
  link.download = "hacktoberfest-2025-contributions.json";
  link.click();
  URL.revokeObjectURL(url);
}

function exportToCSV() {
  const headers = [
    "Repository",
    "PR Number",
    "Status",
    "Date",
    "Language",
    "Type",
    "URL",
    "Notes",
  ];
  const rows = contributions.map((c) => [
    c.repo,
    c.prNumber,
    c.status,
    c.dateFormatted,
    c.language || "",
    c.type || "",
    c.prUrl || "",
    c.notes || "",
  ]);

  let csv = headers.join(",") + "\n";
  rows.forEach((row) => {
    csv += row.map((field) => `"${field}"`).join(",") + "\n";
  });

  const dataBlob = new Blob([csv], { type: "text/csv" });
  const url = URL.createObjectURL(dataBlob);
  const link = document.createElement("a");
  link.href = url;
  link.download = "hacktoberfest-2025-contributions.csv";
  link.click();
  URL.revokeObjectURL(url);
}

// Initialize
loadContributions();
