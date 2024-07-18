import java.util.Scanner;


class Customer
{

    public int id=0;

    public String name;
    public long phone;
    public String[] order = new String[12];
    public int[] order_count = new int [12];
    public int amount=0, table, time;
    public final static int[] date = {5, 9, 2022};

    public void printInfo()
    {
        System.out.println("Customer Id : " + this.id);
        System.out.println("Name : " + this.name);
        System.out.println("Phone : " + this.phone);
        System.out.println("Date : 0" + date[0] + "/0" + date[1] + "/" + date[2]);
        System.out.print("Order Details : ");
        for(int i=0 ; this.order[i]!=null ; i++)
        {
            if(i==0)
                System.out.println((i+1) + ". " + this.order[i] + " :\t" + this.order_count[i] + " Piece");
            else
                System.out.println("                " + (i+1) + ". " + this.order[i] + " :\t" + this.order_count[i] + " Piece");
        }
        System.out.println("Order Amount : " + this.amount);
        System.out.println("Time Slot : " + this.time + ":00 - " + (this.time + 1) + ":00");
        System.out.println("Table No. : " + this.table);
        System.out.println();
    }
}


public class Management
{
    static String[][] menu = {
            {"Pizza Margherita","Pizza Pepperoni","Pizza Capricciosa","Pizza Mushroom","Pizza Marinara","Pizza Vegetariana"},
            {"M.C. Burger","Flamethrower","Cowboy","Sunrise Special","Spicy Garlic","Krapow Burger"}
    };

    static public int[][] menu_price = {{300,350,400,350,400,350},{350,375,300,400,350,375}};

    static Customer[] ob = new Customer[84];


    static int[][] map = new int[12][7];

    static boolean map_full()
    {
        int count=0;
        for(int i=0 ; i<11 ; i++)
        {
            if(map[i][6] != 0)
            {
                count++;
            }
        }
        return count == 12;
    }

    public static void showMenu()
    {
        System.out.println("**MENU**");
        System.out.println("1. PIZZA");
        System.out.println("   a. Pizza Margherita\tRs. 300");
        System.out.println("   b. Pizza Pepperoni\tRs. 350");
        System.out.println("   c. Pizza Capricciosa\tRs. 400");
        System.out.println("   d. Pizza Mushroom\tRs. 350");
        System.out.println("   e. Pizza Marinara\tRs. 400");
        System.out.println("   f. Pizza Vegetariana\tRs. 350");
        System.out.println();
        System.out.println("2. Burger");
        System.out.println("   a. M.C. Burger   \tRs. 350");
        System.out.println("   b. Flamethrower  \tRs. 375");
        System.out.println("   c. Cowboy        \tRs. 300");
        System.out.println("   d. Sunrise Special\tRs. 400");
        System.out.println("   e. Spicy Garlic  \tRs. 350");
        System.out.println("   f. Krapow Burger  \tRs. 375");
        System.out.println("\n-------------------------------");
    }

    public static void bookSlot() {
        if(map_full())
        {
            System.out.println("Whole Slot is Full for the Day\nWe are Extremely Sorry for not Serving You ");
            return;
        }
        int i = 0;
        while(ob[i].id != 0)
            i++;
        Scanner s = new Scanner(System.in);
        System.out.println("**ENTER CUSTOMER DETAILS**");
        ob[i].id = i + 1;
        System.out.print("Name : ");
        ob[i].name = s.nextLine();
        System.out.print("Phone No. : ");
        ob[i].phone = s.nextLong();
        System.out.println("Now Order from Menu --");
        String z;
        int j = 0;
        do {
            showMenu();
            int x;
            do {
                System.out.print("Enter Food Type (1 for Pizza or 2 for Burger) : ");
                x = s.nextInt();
                if(x != 1 && x != 2)
                    System.out.println("Invalid Input");
            } while (x != 1 && x != 2);
            String y;
            if (x==1)
            {
                do {
                    System.out.print("Now Select which Pizza (a to f) : ");
                    y = s.next();
                } while (charToInt(y) == -1);
            }
            else
            {
                do {
                    System.out.print("Now Select which Burger (a to f) : ");
                    y = s.next();
                } while (charToInt(y) == -1);
            }
            ob[i].order[j] = menu[x - 1][charToInt(y)];
            int m;
            do {
                System.out.print("Enter Quantity : ");
                m = s.nextInt();
                if(m<1)
                {
                    System.out.println("Invalid Input");
                    continue;
                }
                break;
            }while (true);
            ob[i].order_count[j] = m;
            ob[i].amount += menu_price[x - 1][charToInt(y)] * m;
            j++;
            do {
                System.out.print("Want to Order more (y or n) : ");
                z = s.next();
                if (z.equals("y"))
                    break;
                else if (z.equals("n"))
                    break;
                System.out.println("Invalid Input");
            }while(true);
        } while (z.equals("y"));
        System.out.println("Your Total Bill Amount : " + ob[i].amount);
        int temp;
        do {
            System.out.println("\n*Note :- Table allot for 1 hour and Restaurent open from 11:00 and close in 23:00\n");
            System.out.print("Enter Time for Booking (11 to 22) : ");
            temp = s.nextInt();
            if(temp<11 || temp>22)
            {
                System.out.println("Invalid Input , Retry");
                continue;
            }
            if(map[temp-11][6]!=0)
            {
                System.out.println("This Time Slot is Already Full.\nTip:- Change your Time Slot");
                System.out.println("--------------------------------");
                continue;
            }
            break;
        }while (true);
        j = 0;
        while(map[temp-11][j] != 0)
            j++;
        map[temp-11][j] = ob[i].id;
        ob[i].table = j+1;
        ob[i].time = temp;
        System.out.print("\nYour Slot is Booked in Time : " + ob[i].time + ":00 - " + ((ob[i].time)+1) + ":00");
        System.out.println(" and Table no. : " + ob[i].table + "\n");
    }

    static void tableAvailable()
    {
        if (map_full())
        {
            System.out.println("All Table are Full");
            return;
        }
        System.out.println("Table Available in Time Slot :");
        for (int i = 0 ; i<12 ; i++)
        {
            int count=-1;
            for (int j = 0 ; j<7 ; j++)
            {
                if(map[i][j] == 0)
                {
                    count = j;
                    break;
                }
            }
            if (count>=0)
            {
                System.out.println("Time Slot " + (i+11) + ":00-" + (i+12) + ":00" + " : " + (7-count) + " Table Available");
            }
        }
        System.out.println();
    }

    static void totalAmount()
    {
        if(ob[0].id == 0)
        {
            System.out.println("There is NO Slot Booked. So, No Collection\n");
            return;
        }
        int i=0 , amount = 0;
        while (ob[i].id != 0)
        {
            amount += ob[i].amount;
            i++;
        }
        System.out.println("Total Amount Collection : " + amount + "\n");
    }

    static void winner()
    {
        if(ob[0].id == 0)
        {
            System.out.println("There is NO Slot Booked. So, No Collection\n");
            return;
        }
        int max=0, index = 0, i=0;
        while (ob[i].id != 0)
        {
            if (i==0)
            {
                max = ob[i].amount;
                i++;
                continue;
            }
            if(ob[i].amount>max)
            {
                max = ob[i].amount;
                index = i;
            }
            i++;
        }
        System.out.println("Winner is " + ob[index].name + " , (ID = " + ob[index].id + ")\n");
    }
    static void nameDetail()
    {
        if(ob[0].id == 0)
        {
            System.out.println("There is NO Booking has done for any Customer\n");
            return;
        }
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Name : ");
        String name = s.nextLine();
        int i=0;
        while(ob[i].id != 0)
        {
            if(ob[i].name.compareToIgnoreCase(name)==0)
            {
                System.out.println("\nCustomer Details :");
                ob[i].printInfo();
                return;
            }
            i++;
        }
        System.out.println("Sorry, NO Such Customer has done Booking OR Try with Correct Name\n");
    }

    static void idDetail()
    {
        if(ob[0].id == 0)
        {
            System.out.println("There is NO Booking has done for any Customer\n");
            return;
        }
        Scanner s = new Scanner(System.in);
        System.out.print("Enter ID : ");
        int id = s.nextInt();
        int i=0;
        while(ob[i].id != 0)
        {
            if(id == ob[i].id)
            {
                System.out.println("\nCustomer Details :");
                ob[i].printInfo();
                return;
            }
            i++;
        }
        System.out.println("Sorry, NO Such Customer has done Booking OR Try with Correct Name\n");
    }

    static int charToInt(String y)
    {
        int temp = -1;
        switch (y) {
            case "a" -> temp = 0;
            case "b" -> temp = 1;
            case "c" -> temp = 2;
            case "d" -> temp = 3;
            case "e" -> temp = 4;
            case "f" -> temp = 5;
            default -> System.out.println("Invalid Input");
        }
        return temp;
    }

    public static void main(String [] args)
    {
        for(int i=0 ; i<84 ; i++)
        {
            ob[i] = new Customer();
        }
        do
        {
            Scanner s = new Scanner(System.in);
            System.out.println("****SUNRISE RESTAURANT****");
            System.out.println("1. Show Menu of Foods");
            System.out.println("2. Book Slot");
            System.out.println("3. Show Table Availability");
            System.out.println("4. Show Total Amount Collection");
            System.out.println("5. Gift Winner");
            System.out.println("6. Search Customer Details");
            System.out.println("0. Exit");
            System.out.print("   Enter Option : ");
            int x = s.nextInt();
            System.out.println();

            switch (x) {
                case 1 -> {
                    showMenu();
                    do {
                        System.out.println("1. Want to Book Slot");
                        System.out.println("0. Exit to Main Menu");
                        System.out.print("   Enter Option : ");
                        int y = s.nextInt();
                        System.out.println();
                        if (y == 1) {
                            bookSlot();
                            break;
                        } else if (y != 0)
                            System.out.println("Invalid input, Please enter correctly");
                        else
                            break;
                    } while (true);
                }
                case 2 -> bookSlot();
                case 3 -> tableAvailable();
                case 4 -> totalAmount();
                case 5 -> winner();
                case 6 -> {
                    int y;
                    do {
                        System.out.println("1. Search by Customer Name ");
                        System.out.println("2. Search by Customer ID ");
                        System.out.print("   Enter Option : ");
                        y = s.nextInt();
                    }while(y!=1 && y!=2);
                    if (y==1)
                        nameDetail();
                    else
                        idDetail();
                }
                case 0 -> {
                    System.out.println("Please Visit Again!!!");
                    return;
                }
                default -> System.out.println("Invalid input, Please enter correctly");
            }
        }while(true);
    }
}