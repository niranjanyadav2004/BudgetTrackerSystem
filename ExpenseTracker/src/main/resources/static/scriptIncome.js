document.addEventListener("DOMContentLoaded", () => {
    const IncomeForm = document.getElementById("income-form");
    const IncomesDiv = document.getElementById("allIncome");
    const IncomeIdInput = document.getElementById("incomeId");
    console.log("Hello");

    fetchIncomes();

 
    IncomeForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = IncomeIdInput.value;
        const title = document.getElementById("incomeTitle").value;
        const description = document.getElementById("incomeDescription").value;
        const amount = document.getElementById("incomeAmount").value;
        const date = document.getElementById("incomeDate").value;
        const category = document.getElementById("category").value;

        const income = { title, description, amount, date,category };

        if (id) {
            await updateExpense(id, income);
        } else {
            await createExpense(income);
        }

        IncomeForm.reset();
        IncomeIdInput.value = "";
        fetchIncomes();
    });

    // Fetch all expenses
    async function fetchIncomes() {
        const response = await fetch("/api/income/all");
        const incomes = await response.json();
        IncomesDiv.innerHTML = "";
        incomes.forEach(income => {
			console.log(income);
            const incomeDiv = document.createElement("div");
            incomeDiv.className = "expense";
            incomeDiv.innerHTML = `
                <div style="display:flex;">
                    <span>${income.title} - $${income.amount}</span>
                </div>    
                <div> 
                    <button type="button" class="btn btn-primary" onclick="editExpense(${income.id})" >Edit</button>
                    <button type="button" class="btn btn-danger" onclick="deleteExpense(${income.id})" >Delete</button>
                </div>  
            `;
            IncomesDiv.appendChild(incomeDiv);
        });
    }

    // Create new expense
    async function createExpense(income) {
        await fetch("/api/income", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(income)
        });
    }

    // Update expense
    async function updateExpense(id, income) {
        await fetch(`/api/income/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(income)
        });
    }

    // Delete expense
    window.deleteExpense = async (id) => {
        await fetch(`/api/income/${id}`, {
            method: "DELETE"
        });
        fetchIncomes();
    }


    // Edit expense
    window.editExpense = (id) => {
        window.location.href = `editIncome.html?id=${id}`;
    }
});
