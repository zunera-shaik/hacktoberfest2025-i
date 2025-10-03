import React, { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [expenses, setExpenses] = useState([]);
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("General");
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);
  const [editId, setEditId] = useState(null);
  const [filter, setFilter] = useState("all");
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("date");
  const [budget, setBudget] = useState(1000);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");

  // Categories with colors
  const categories = [
    { value: "Food", color: "#FF6B6B", icon: "🍔" },
    { value: "Transport", color: "#4ECDC4", icon: "🚗" },
    { value: "Shopping", color: "#45B7D1", icon: "🛍️" },
    { value: "Entertainment", color: "#96CEB4", icon: "🎬" },
    { value: "Bills", color: "#FFEAA7", icon: "📄" },
    { value: "Health", color: "#DDA0DD", icon: "🏥" },
    { value: "Travel", color: "#98D8C8", icon: "✈️" },
    { value: "General", color: "#B2B2B2", icon: "📦" }
  ];

  // Load expenses from localStorage on component mount
  useEffect(() => {
    const savedExpenses = localStorage.getItem('expenses');
    const savedBudget = localStorage.getItem('budget');
    if (savedExpenses) {
      setExpenses(JSON.parse(savedExpenses));
    }
    if (savedBudget) {
      setBudget(parseFloat(savedBudget));
    }
  }, []);

  // Save expenses to localStorage whenever expenses change
  useEffect(() => {
    localStorage.setItem('expenses', JSON.stringify(expenses));
  }, [expenses]);

  // Save budget to localStorage
  useEffect(() => {
    localStorage.setItem('budget', budget.toString());
  }, [budget]);

  // Show toast notification
  const showNotification = (message) => {
    setToastMessage(message);
    setShowToast(true);
    setTimeout(() => setShowToast(false), 3000);
  };

  // Add new expense
  const addExpense = () => {
    if (!description.trim() || !amount || amount <= 0) {
      showNotification("Please enter valid description and amount!");
      return;
    }

    if (editId !== null) {
      const updatedExpenses = expenses.map((expense) =>
        expense.id === editId
          ? { ...expense, description, amount: parseFloat(amount), category, date }
          : expense
      );
      setExpenses(updatedExpenses);
      setEditId(null);
      showNotification("Expense updated successfully! 💰");
    } else {
      const newExpense = {
        id: Date.now(),
        description: description.trim(),
        amount: parseFloat(amount),
        category,
        date,
        createdAt: new Date().toISOString()
      };
      setExpenses([newExpense, ...expenses]);
      showNotification("Expense added successfully! ✅");
    }
    resetFields();
  };

  // Delete expense with confirmation
  const deleteExpense = (id) => {
    if (window.confirm("Are you sure you want to delete this expense?")) {
      setExpenses(expenses.filter((expense) => expense.id !== id));
      showNotification("Expense deleted! 🗑️");
    }
  };

  // Edit expense
  const editExpense = (id) => {
    const expense = expenses.find((expense) => expense.id === id);
    setDescription(expense.description);
    setAmount(expense.amount.toString());
    setCategory(expense.category);
    setDate(expense.date);
    setEditId(id);
    showNotification("Editing expense... ✏️");
  };

  // Reset input fields
  const resetFields = () => {
    setDescription("");
    setAmount("");
    setCategory("General");
    setDate(new Date().toISOString().split('T')[0]);
  };

  // Cancel edit
  const cancelEdit = () => {
    setEditId(null);
    resetFields();
    showNotification("Edit cancelled");
  };

  // Filter and sort expenses
  const filteredAndSortedExpenses = expenses
    .filter(expense => {
      const matchesFilter = filter === "all" || expense.category === filter;
      const matchesSearch = expense.description.toLowerCase().includes(searchTerm.toLowerCase());
      return matchesFilter && matchesSearch;
    })
    .sort((a, b) => {
      switch (sortBy) {
        case "amount":
          return b.amount - a.amount;
        case "date":
          return new Date(b.date) - new Date(a.date);
        case "category":
          return a.category.localeCompare(b.category);
        default:
          return new Date(b.createdAt) - new Date(a.createdAt);
      }
    });

  // Calculate totals
  const totalExpenses = expenses.reduce((acc, expense) => acc + expense.amount, 0);
  const remainingBudget = budget - totalExpenses;
  const budgetPercentage = (totalExpenses / budget) * 100;

  // Calculate category totals
  const categoryTotals = categories.map(cat => ({
    ...cat,
    total: expenses
      .filter(exp => exp.category === cat.value)
      .reduce((sum, exp) => sum + exp.amount, 0)
  }));

  // Format currency
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  // Format date
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  return (
    <div className="App">
      {/* Toast Notification */}
      {showToast && (
        <div className="toast show">
          {toastMessage}
        </div>
      )}

      <h1>💰 Expense Tracker</h1>

      {/* Budget Section */}
      <div className="budget-section">
        <div className="budget-input">
          <label>Monthly Budget:</label>
          <input
            type="number"
            value={budget}
            onChange={(e) => setBudget(parseFloat(e.target.value) || 0)}
            min="0"
            step="10"
          />
        </div>
        <div className="budget-progress">
          <div className="progress-bar">
            <div 
              className="progress-fill" 
              style={{ 
                width: `${Math.min(budgetPercentage, 100)}%`,
                background: budgetPercentage > 80 ? 'var(--danger-gradient)' : 
                           budgetPercentage > 60 ? 'var(--secondary-gradient)' : 'var(--success-gradient)'
              }}
            ></div>
          </div>
          <div className="budget-stats">
            <span>Spent: {formatCurrency(totalExpenses)}</span>
            <span>Remaining: {formatCurrency(remainingBudget)}</span>
            <span>{budgetPercentage.toFixed(1)}% of budget</span>
          </div>
        </div>
      </div>

      {/* Expense Form */}
      <div className="form fade-in">
        <div className="input-group">
          <input
            type="text"
            placeholder="What did you spend on?"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="slide-in"
          />
          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            min="0"
            step="0.01"
            className="slide-in"
          />
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            className="slide-in"
          />
          <select 
            value={category} 
            onChange={(e) => setCategory(e.target.value)}
            className="slide-in"
          >
            {categories.map(cat => (
              <option key={cat.value} value={cat.value}>
                {cat.icon} {cat.value}
              </option>
            ))}
          </select>
        </div>
        
        <div className="button-group">
          <button onClick={addExpense} className={editId !== null ? "secondary" : ""}>
            {editId !== null ? "💾 Update Expense" : "➕ Add Expense"}
          </button>
          {editId !== null && (
            <button onClick={cancelEdit} className="secondary">
              ❌ Cancel
            </button>
          )}
        </div>
      </div>

      {/* Controls Section */}
      <div className="controls">
        <div className="search-box">
          <i className="fas fa-search"></i>
          <input
            type="text"
            placeholder="Search expenses..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        
        <select 
          value={filter} 
          onChange={(e) => setFilter(e.target.value)}
          className="filter-select"
        >
          <option value="all">All Categories</option>
          {categories.map(cat => (
            <option key={cat.value} value={cat.value}>
              {cat.icon} {cat.value}
            </option>
          ))}
        </select>

        <select 
          value={sortBy} 
          onChange={(e) => setSortBy(e.target.value)}
          className="sort-select"
        >
          <option value="date">Sort by Date</option>
          <option value="amount">Sort by Amount</option>
          <option value="category">Sort by Category</option>
        </select>
      </div>

      {/* Category Summary */}
      <div className="category-summary">
        <h2>📊 Spending by Category</h2>
        <div className="category-badges">
          {categoryTotals.map(cat => (
            <div 
              key={cat.value} 
              className={`category-badge ${filter === cat.value ? 'active' : ''}`}
              onClick={() => setFilter(cat.value)}
              style={{ 
                background: cat.total > 0 ? cat.color : 'var(--glass-bg)',
                color: cat.total > 0 ? 'white' : 'var(--text-secondary)'
              }}
            >
              <span className="category-icon">{cat.icon}</span>
              <span className="category-name">{cat.value}</span>
              <span className="category-amount">{formatCurrency(cat.total)}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Expense List */}
      <div className="expense-list">
        <h2>
          📋 Your Expenses 
          <span className="expense-count">({filteredAndSortedExpenses.length})</span>
        </h2>
        
        {filteredAndSortedExpenses.length === 0 ? (
          <div className="empty-state">
            <i className="fas fa-receipt"></i>
            <p>No expenses found</p>
            <p className="empty-subtitle">
              {expenses.length === 0 
                ? "Add your first expense to get started!" 
                : "Try changing your filters or search term"}
            </p>
          </div>
        ) : (
          <ul>
            {filteredAndSortedExpenses.map((expense) => {
              const categoryInfo = categories.find(cat => cat.value === expense.category);
              return (
                <li key={expense.id} className="fade-in">
                  <div className="expense-info">
                    <div className="expense-main">
                      <span className="expense-name">{expense.description}</span>
                      <span className="expense-amount">{formatCurrency(expense.amount)}</span>
                    </div>
                    <div className="expense-meta">
                      <span 
                        className="expense-category"
                        style={{ background: categoryInfo?.color }}
                      >
                        {categoryInfo?.icon} {expense.category}
                      </span>
                      <span className="expense-date">{formatDate(expense.date)}</span>
                    </div>
                  </div>
                  <div className="expense-actions">
                    <button 
                      onClick={() => editExpense(expense.id)}
                      className="edit-btn"
                    >
                      ✏️ Edit
                    </button>
                    <button 
                      onClick={() => deleteExpense(expense.id)}
                      className="delete-btn"
                    >
                      🗑️ Delete
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
        )}
      </div>

      {/* Financial Summary */}
      <div className="summary">
        <h2>💰 Financial Summary</h2>
        <div className="summary-content">
          <div className="summary-item">
            <h3>Total Expenses</h3>
            <div className="amount">{formatCurrency(totalExpenses)}</div>
          </div>
          <div className="summary-item">
            <h3>Monthly Budget</h3>
            <div className="amount">{formatCurrency(budget)}</div>
          </div>
          <div className="summary-item">
            <h3>Remaining</h3>
            <div 
              className="amount" 
              style={{ 
                color: remainingBudget < 0 ? '#e74c3c' : '#27ae60'
              }}
            >
              {formatCurrency(remainingBudget)}
            </div>
          </div>
          <div className="summary-item">
            <h3>Average Daily</h3>
            <div className="amount">
              {formatCurrency(totalExpenses / new Date().getDate())}
            </div>
          </div>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="quick-actions">
        <button 
          onClick={() => {
            if (expenses.length > 0 && window.confirm("Clear all expenses?")) {
              setExpenses([]);
              showNotification("All expenses cleared! 🧹");
            }
          }}
          className="secondary"
        >
          🧹 Clear All
        </button>
        <button 
          onClick={() => {
            const dataStr = JSON.stringify(expenses, null, 2);
            const blob = new Blob([dataStr], { type: "application/json" });
            const url = URL.createObjectURL(blob);
            const link = document.createElement("a");
            link.href = url;
            link.download = "expenses-backup.json";
            link.click();
            showNotification("Expenses exported! 📤");
          }}
          className="secondary"
        >
          📤 Export Data
        </button>
      </div>
    </div>
  );
}

export default App;
