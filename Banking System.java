mandleyimport java.util.*;
class BankingSystem
{
    private static String[] getAccount(Map<String, String[]> accounts, String accountNumber) 
    {
        return accounts.get(accountNumber);
    }

    private static void updateBalance(String[] accountData, double newBalance) 
    {
        accountData[1] = String.format("%.2f", newBalance); 
    }

    public static void main(String[] args) 
    {
        Map<String, String[]> accounts = new HashMap<>(); 

        try (Scanner sc = new Scanner(System.in)) 
        {
            boolean running = true;
            
            while (running) 
            {
                System.out.println("MAIN MENU");
                System.out.println("1. Create New Account");
                System.out.println("2. Deposit Funds");
                System.out.println("3. Withdraw Funds");
                System.out.println("4. Transfer Funds");
                System.out.println("5. View Account Details");
                System.out.println("6. Exit System");
                System.out.print("Enter choice (1-6): ");

                try 
                {
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) 
                    {
                        case 1:
                            System.out.println("CREATE NEW ACCOUNT");
                            System.out.print("Enter Customer Name: ");
                            String name = sc.nextLine().trim();
                            System.out.print("Enter Initial Deposit Amount (min 100.00 ₹): "); 
                            double initialDeposit = sc.nextDouble();
                            sc.nextLine();

                            if (initialDeposit < 100.00) 
                            {
                                System.out.println("[ERROR] Initial deposit must be at least 100.00 ₹.");
                                break;
                            }

                            String newAccNum = String.valueOf(1000 + accounts.size() + 1);
                            accounts.put(newAccNum, new String[]{name, String.format("%.2f", initialDeposit)});

                            System.out.println("[SUCCESS] Account created!");
                            System.out.println("Account Holder: " + name);
                            System.out.println("Account Number: " + newAccNum);
                            System.out.println("Initial Balance: ₹" + String.format("%.2f", initialDeposit)); 
                            break;

                        case 2:
                            System.out.println("DEPOSIT FUNDS");
                            System.out.print("Enter Account Number: ");
                            String depAccNum = sc.nextLine().trim();

                            String[] depAccount = getAccount(accounts, depAccNum);
                            if (depAccount == null) 
                            {
                                System.out.println("[ERROR] Account not found.");
                                break;
                            }

                            System.out.print("Enter Deposit Amount: ");
                            double depAmount = sc.nextDouble();
                            sc.nextLine();

                            if (depAmount <= 0) 
                            {
                                System.out.println("Deposit amount must be positive.");
                                break;
                            }

                            double depCurrentBalance = Double.parseDouble(depAccount[1]);
                            double depNewBalance = depCurrentBalance + depAmount;
                            updateBalance(depAccount, depNewBalance);

                            System.out.println("Deposit successful.");
                            System.out.println("New Balance for Account " + depAccNum + ": ₹" + depAccount[1]); 
                            break;

                        case 3:
                            System.out.println("WITHDRAW FUNDS");
                            System.out.print("Enter Account Number: ");
                            String witAccNum = sc.nextLine().trim();

                            String[] witAccount = getAccount(accounts, witAccNum);
                            if (witAccount == null) 
                            {
                                System.out.println("[ERROR] Account not found.");
                                break;
                            }

                            System.out.print("Enter Withdrawal Amount: ");
                            double witAmount = sc.nextDouble();
                            sc.nextLine();

                            if (witAmount <= 0) 
                            {
                                System.out.println("[ERROR] Withdrawal amount must be positive.");
                                break;
                            }

                            double witCurrentBalance = Double.parseDouble(witAccount[1]);
                            
                            if (witAmount > witCurrentBalance) 
                            {
                                System.out.println("[ERROR] Insufficient funds. Current balance: ₹" + witAccount[1]); 
                                break;
                            }

                            double witNewBalance = witCurrentBalance - witAmount;
                            updateBalance(witAccount, witNewBalance);

                            System.out.println("[SUCCESS] Withdrawal successful.");
                            System.out.println("New Balance for Account " + witAccNum + ": ₹" + witAccount[1]); 
                            break;

                        case 4:
                            System.out.println("TRANSFER FUNDS");
                            System.out.print("Enter Source Account Number: ");
                            String srcAccNum = sc.nextLine().trim();
                            System.out.print("Enter Target Account Number: ");
                            String tarAccNum = sc.nextLine().trim();

                            String[] srcAccount = getAccount(accounts, srcAccNum);
                            String[] tarAccount = getAccount(accounts, tarAccNum);

                            if (srcAccount == null || tarAccount == null) 
                            {
                                System.out.println("[ERROR] One or both accounts not found.");
                                break;
                            }
                            if (srcAccNum.equals(tarAccNum)) 
                            {
                                System.out.println("[ERROR] Cannot transfer funds to the same account.");
                                break;
                            }

                            System.out.print("Enter Transfer Amount: ");
                            double transAmount = sc.nextDouble();
                            sc.nextLine();

                            if (transAmount <= 0) 
                            {
                                System.out.println("[ERROR] Transfer amount must be positive.");
                                break;
                            }

                            double srcCurrentBalance = Double.parseDouble(srcAccount[1]);

                            if (transAmount > srcCurrentBalance) 
                            {
                                System.out.println("[ERROR] Insufficient funds in source account. Balance: ₹" + srcAccount[1]);
                                break;
                            }

                            double srcNewBalance = srcCurrentBalance - transAmount;
                            double tarCurrentBalance = Double.parseDouble(tarAccount[1]);
                            double tarNewBalance = tarCurrentBalance + transAmount;

                            updateBalance(srcAccount, srcNewBalance);
                            updateBalance(tarAccount, tarNewBalance);

                            System.out.println("[SUCCESS] Transfer successful!");
                            System.out.println("Source Account (" + srcAccNum + ") New Balance: ₹" + srcAccount[1]);
                            System.out.println("Target Account (" + tarAccNum + ") New Balance: ₹" + tarAccount[1]);
                            break;

                        case 5:
                            System.out.println("VIEW ACCOUNT DETAILS");
                            System.out.print("Enter Account Number: ");
                            String viewAccNum = sc.nextLine().trim();

                            String[] viewAccount = getAccount(accounts, viewAccNum);
                            if (viewAccount == null) 
                            {
                                System.out.println("[ERROR] Account not found.");
                                break;
                            }

                            System.out.println("[DETAILS] Account Found:");
                            System.out.println("Account Number: " + viewAccNum);
                            System.out.println("Customer Name: " + viewAccount[0]);
                            System.out.println("Current Balance: ₹" + viewAccount[1]); 
                            break;

                        case 6:
                            System.out.println("Thank you for using the BANKY System. Goodbye!");
                            running = false;
                            break;

                        default:
                            System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 6.");
                    }
                } catch (java.util.InputMismatchException e) 
                {
                    System.out.println("[ERROR] Invalid input. Please enter a valid number for the choice or amount.");
                    sc.nextLine();
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}