document.addEventListener("DOMContentLoaded", () => {
    const expenseForm = document.getElementById("expense-form");
    const expensesDiv = document.getElementById("allExp");
    const expenseIdInput = document.getElementById("expenseId");

    fetchExpenses();

 
    expenseForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = expenseIdInput.value;
        const title = document.getElementById("expenseTitle").value;
        const description = document.getElementById("expenseDescription").value;
        const amount = document.getElementById("expenseAmount").value;
        const date = document.getElementById("expenseDate").value;
        const category = document.getElementById("category").value;

        const expense = { title, description, amount, date,category };

        if (id) {
            await updateExpense(id, expense);
        } else {
            await createExpense(expense);
        }

        expenseForm.reset();
        expenseIdInput.value = "";
        fetchExpenses();
    });

    // Fetch all expenses
    async function fetchExpenses() {
        const response = await fetch("/api/expense/all");
        const expenses = await response.json();
        expensesDiv.innerHTML = "";
        expenses.forEach(expense => {
			console.log(expense);
            const expenseDiv = document.createElement("div");
            expenseDiv.className = "expense";
            expenseDiv.innerHTML = `
                <div style="display:flex;">
                    <span>${expense.title} - $${expense.amount}</span>
                </div>    
                <div> 
                    <button type="button" class="btn btn-primary" onclick="editExpense(${expense.id})" >Edit</button>
                    <button type="button" class="btn btn-danger" onclick="deleteExpense(${expense.id})" >Delete</button>
                </div>  
            `;
            expensesDiv.appendChild(expenseDiv);
        });
    }

    // Create new expense
    async function createExpense(expense) {
        await fetch("/api/expense", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(expense)
        });
    }

    // Update expense
    async function updateExpense(id, expense) {
        await fetch(`/api/expense/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(expense)
        });
    }

    // Delete expense
    window.deleteExpense = async (id) => {
        await fetch(`/api/expense/${id}`, {
            method: "DELETE"
        });
        fetchExpenses();
    }


    // Edit expense
    window.editExpense = (id) => {
        window.location.href = `editExpense.html?id=${id}`;
    }
});
