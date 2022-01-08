public class BigNum {

    private class Node {
        private Node head;
        private String sign;
        int length;
        int data;
        Node next;

        private void NumSign(int num) {
            if (num < 0) {
                noderev.sign = "-";
            } else {
                noderev.sign = "";
            }
        }


        private void insert(int data) { // Inserts a new Node into LinkedList
            Node node = new Node(); // Creates a new Node
            node.data = data; // Initializes the data
            node.next = null; // Sets the next Node to null
            if (head == null) {  // If the head is null
                head = node; // Then set the head equal to the new Node
            } else { // If the head is not null
                Node n = head; // Set Node n equal to the head
                while (n.next != null) { // and while the next node is not null
                    n = n.next; // set the next node to n to iterate through the LinkedList
                }
                n.next = node; // then set the n.next equal to the new node
            }
        }

        private String printList() {
            String s = ""; // Create an empty string
            Node temp = head; // Set the temporary Node to head
            while (temp != null) { // and while the temp is not null
                s += temp.data; // Add the data of the node to the string
                temp = temp.next; // Advance temp to the next node
            }
            String str = ""; // Create another empty string
            for (int i = s.length(); i > 0; i--) { // Puts the string s in forward order
                str += s.substring(i - 1, i);
            }
            return noderev.sign + str;
        }
    }

    private Node noderev;

    // Construct an empty BigNum.
    public BigNum() {
        noderev = new Node();
    }


    // Construct a BigNum from an int, which can be positive or negative. (Do not throw an
    // IllegalArgumentException if n < 0.)
    public BigNum(int n) {
        noderev = new Node(); // Creates a new Node to represent the BigNum reversed
        String number = Integer.toString(n); // Converts n to a string
        if (number.contains("-")) {
            noderev.NumSign(-1);
            number = number.substring(1);
        } else {
            noderev.NumSign(1);
        }
        for (int i = number.length(); i > 0; i--) {
            // inserts numbers in reverse order into noderev
            int x = Integer.parseInt(number.substring(i - 1, i));
            noderev.insert(x);
            noderev.length++;
        }
    }


    // Construct a BigNum from a String, which must contain only
// characters between 0 and 9. It may begin with the character "-".
// Throw an IllegalArgumentException for any other character.
    public BigNum(String s) {
        noderev = new Node(); // Creates a new node to represent the BigNum reversed
        if (s.charAt(0) == '-') {
            s = s.substring(1);
            noderev.NumSign(-1);
        } else {
            noderev.NumSign(1);
        }
        for (int i = 0; i < s.length(); i++) {
            char check = s.charAt(i);
            if (!Character.isDigit(check)) {
                throw new IllegalArgumentException("Illegal String");
            }
        }
        int lengthCount = s.length();
        int n = 0; // creates an int n that is 0
        if (lengthCount != 1 && s.charAt(n) == '0') {
            while (s.charAt(n) == '0' && lengthCount != 1) { // gets rid of 0's at the front of the integer string
                n++;
                lengthCount--;
            }
        }
        s = s.substring(n);
        for (int i = s.length(); i > 0; i--) {
            // inserts numbers into the Linked List in reverse order
            int x = Integer.parseInt(s.substring(i - 1, i));
            noderev.insert(x);
            noderev.length++;
        }
    }

    // Convert this BigNum to a String
    public String toString() {
        return noderev.printList();
    }

    // Compare this BigNum to another BigNum, returning 0 if they are equal,
// a value > 0 if this > other, or a value < 0 if this < other.
    public int compareTo(BigNum other) {
        Node x = this.noderev.head;
        Node y = other.noderev.head;
        if (this.noderev.sign == "-" && other.noderev.sign != "-") {
            return -1; // return -1 if this is negative and other is positive
        }
        if (other.noderev.sign == "-" && this.noderev.sign != "-") {
            return 1;  // return 1 if this is positive and other is negative
        }
        if (this.noderev.length > other.noderev.length) {
            return 1; // return 1 if this is longer than other
        }
        if (other.noderev.length > this.noderev.length) {
            return -1; // return -1 if other is longer than this
        }
        if (this.noderev.head.data > other.noderev.head.data) {
            return 1; // return 1 if the data in the head of this is greater than the data in the head of other
        }
        if (other.noderev.head.data > this.noderev.head.data) {
            return -1; // return -1 if the data in the head of other is greater than the data in the head of this
        }
        while (this.noderev.head.next != null) { // compare each digit of this and other until one digit is found to be bigger than the other
            this.noderev.head = this.noderev.head.next;
            other.noderev.head = other.noderev.head.next;
            if (this.noderev.head.data > other.noderev.head.data) {
                this.noderev.head = x;
                other.noderev.head = y;
                return 1;
            }
            if (other.noderev.head.data > this.noderev.head.data) {
                this.noderev.head = x;
                other.noderev.head = y;
                return -1;
            }
        }
        this.noderev.head = x;
        other.noderev.head = y;
        return 0; // If no digits are found to be different, return 0
    }

    // Add this BigNum to another BigNum, returning a new BigNum which contains the sum of the two
    public BigNum add(BigNum other) {
        BigNum added = new BigNum(); // create a new BigNum added
        int sum; // create an int that will hold the sum of two digits
        int carry = 0; // create an int that will hold the carry value of two ints; either 1 or 0
        if (this.noderev.sign == "-" && other.noderev.sign != "-") {
            other.subtract(this);
        }
        if (this.noderev.sign != "-" && other.noderev.sign == "-") {
            this.subtract(other);
        }
        if (this.noderev.sign == "-" && other.noderev.sign == "-") {
            added.noderev.NumSign(-1);
        } else {
            added.noderev.NumSign(1);
        }
        sum = this.noderev.head.data + other.noderev.head.data + carry; // the sum is the data of this plus the data of other plus the carry; this gets the sum of the ones place before iterating through the rest
        added.noderev.insert(sum % 10); // insert the remainder of the sum divided by ten; if less then 10, just insert the number, if greater than 10 inserts the ones digit of the sum
        carry = sum / 10; // determines if the carry is 1 or 0 by dividing the sum by 10; if less than 10, carry is 0, if greater than 10, carry is 1
        while (this.noderev.head.next != null && other.noderev.head.next != null) { // iterates through this BigNum and other BigNum
            this.noderev.head = this.noderev.head.next; // Advanced both BigNums to the next digit
            other.noderev.head = other.noderev.head.next;
            sum = this.noderev.head.data + other.noderev.head.data + carry; // Sums the two digits plus the carry
            added.noderev.insert(sum % 10); // Inserts the remainder of the sum divided by 10
            carry = sum / 10; // Determines if another carry is needed
        }
        if (this.noderev.head.next != null) { // If this BigNum has a greater length than other BigNum, insert the rest of the digits determining if the carry is still needed
            while (this.noderev.head.next != null) {
                this.noderev.head = this.noderev.head.next;
                sum = this.noderev.head.data + carry;
                added.noderev.insert(sum % 10);
                carry = sum / 10;
            }
        } else {
            while (other.noderev.head.next != null) { // If other BigNum has a greater length than this BigNum, insert the rest of the digits determining if the carry is still needed
                other.noderev.head = other.noderev.head.next;
                sum = other.noderev.head.data + carry;
                added.noderev.insert(sum % 10);
                carry = sum / 10;
            }
        }
        if (carry == 1) { // If there is a carry still left over after getting the sum of the last two digits
            added.noderev.insert(carry); // insert 1 to the very end of the BigNum
        }
        return added; // Return the newly created BigNum that is the result of adding this BigNum and other BigNum
    }

    // Subtract BigNum other from this, returning
// a new BigNum which contains the difference of the two
    public BigNum subtract(BigNum other) {
        BigNum difference = new BigNum();
        BigNum difference2 = new BigNum();
        Node h = null;
        difference.noderev.sign = "";
        int d;
        if (other.noderev.sign == "-") {
            other.noderev.sign = "";
            difference = this.add(other);
            return difference;
        }
        if (this.noderev.sign == "-") {
            other.noderev.sign = "-";
            difference = this.add(other);
            return difference;
        }

        if (other.compareTo(this) == 1) {
            difference = other.subtract(this);
            difference.noderev.sign = "-";
            return difference;
        }
        while (this.noderev.head.next != null && other.noderev.head.next != null) {
            if (other.noderev.head.data > this.noderev.head.data) {
                this.noderev.head.data = this.noderev.head.data + 10;
                this.noderev.head.next.data = this.noderev.head.next.data - 1;
            }
            d = this.noderev.head.data - other.noderev.head.data;
            difference.noderev.insert(d);
            difference.noderev.length++;
            this.noderev.head = this.noderev.head.next;
            other.noderev.head = other.noderev.head.next;
        }
        if (other.noderev.head.data > this.noderev.head.data) {
            if (this.noderev.head.next.data == 0) {
                Node spot = this.noderev.head.next;
                while (this.noderev.head.next.data == 0) {
                    this.noderev.head.next.data = 9;
                    this.noderev.head.next = this.noderev.head.next.next;
                }
                this.noderev.head.next.data = this.noderev.head.next.data - 1;
                this.noderev.head.data = this.noderev.head.data + 10;
                this.noderev.head.next = spot;

            }
        }
        d = this.noderev.head.data - other.noderev.head.data;
        difference.noderev.insert(d);
        difference.noderev.length++;
        this.noderev.head = this.noderev.head.next;
        other.noderev.head = other.noderev.head.next;
        if (this.noderev.head != null) {
            if (this.noderev.head.next != null) { // If this BigNum has a greater length than other BigNum, insert the rest of the digits determining if the carry is still needed
                while (this.noderev.head.next != null) {
                    d = this.noderev.head.data;
                    difference.noderev.insert(d);
                    difference.noderev.length++;
                    this.noderev.head = this.noderev.head.next;
                }
            }
        }
        if (this.noderev.head != null) {
            d = this.noderev.head.data;
            difference.noderev.insert(d);
            difference.noderev.length++;
        }
        if (difference.noderev.head.data == 0) {
            while (difference.noderev.head.data == 0) {
                difference.noderev.head = difference.noderev.head.next;
            }
        }
        Node first = difference.noderev.head;
        while (true) {
            while (difference.noderev.head.next != null) {
                difference2.noderev.insert(difference.noderev.head.data);
                difference.noderev.head = difference.noderev.head.next;
            }
            if (difference.noderev.head.data == 0) {
                break;
            } else {
                difference2.noderev.insert(difference.noderev.head.data);
                break;
            }
        }
        difference2.noderev.sign = difference.noderev.sign;
        return difference2;
    }

    // Multiply one digit d times another BigNum other, and return a BigNum
// containing the product
    public BigNum multOneDigit(int d, BigNum other) {
        BigNum product = new BigNum(other.toString());
        BigNum ifZero = new BigNum();
        ifZero.noderev.sign = "";
        Node temp = other.noderev.head;
        if (d == 0) {
            ifZero.noderev.insert(0);
            return ifZero;
        }
        if (d > 0) {
            for (int i = d; i > 1; i--) {
                product = product.add(other);
                other.noderev.head = temp;
            }
        } else {
            for (int i = d; i < -1; i++) {
                product = product.add(other);
                other.noderev.head = temp;
            }
        }
        if (d < 0 && other.noderev.sign == "-") {
            product.noderev.NumSign(1);
        }
        if (d < 0 && other.noderev.sign != "-") {
            product.noderev.NumSign(-1);
        }
        if (d > 0 && other.noderev.sign == "-") {
            product.noderev.NumSign(-1);
        }
        if (d > 0 && other.noderev.sign != "-") {
            product.noderev.NumSign(1);
        }
        return product;
    }

    // Multiply this BigNum by another BigNum, returning the product as a BigNum
    public BigNum multiply(BigNum other) {
        // Build the BigNum that the product will be stored in and two temporary BigNums
        // Get the length of other
        BigNum SuperMult = new BigNum();
        BigNum temp;
        BigNum temp2 = new BigNum();

        if (other.noderev.head.next == null && other.noderev.head.data == 0) {
            SuperMult.noderev.insert(0);
            SuperMult.noderev.sign = "";
            return SuperMult;
        }
        if (this.noderev.head.next == null && this.noderev.head.data == 0) {
            SuperMult.noderev.insert(0);
            SuperMult.noderev.sign = "";
            return SuperMult;
        }
        if (other.noderev.head.next == null) {
            SuperMult = this.multOneDigit(other.noderev.head.data, this);
            if (this.noderev.sign == "-" && other.noderev.sign == "-") {
                SuperMult.noderev.sign = "";
            }
            if (this.noderev.sign == "-" && other.noderev.sign != "-") {
                SuperMult.noderev.sign = "-";
            }
            if (this.noderev.sign != "-" && other.noderev.sign == "-") {
                SuperMult.noderev.sign = "-";
            }
            if (this.noderev.sign != "-" && other.noderev.sign != "-") {
                SuperMult.noderev.sign = "";
            }
            return SuperMult;
        }
        if (this.noderev.head.next == null) {
            SuperMult = other.multOneDigit(this.noderev.head.data, other);
            if (this.noderev.sign == "-" && other.noderev.sign == "-") {
                SuperMult.noderev.sign = "";
            }
            if (this.noderev.sign == "-" && other.noderev.sign != "-") {
                SuperMult.noderev.sign = "-";
            }
            if (this.noderev.sign != "-" && other.noderev.sign == "-") {
                SuperMult.noderev.sign = "-";
            }
            if (this.noderev.sign != "-" && other.noderev.sign != "-") {
                SuperMult.noderev.sign = "";
            }
            return SuperMult;
        }

        other.noderev.length = 0;
        Node h = other.noderev.head;
        while (other.noderev.head != null) {
            other.noderev.length++;
            other.noderev.head = other.noderev.head.next;
        }
        other.noderev.head = h;
        temp2.noderev.sign = "";

        // Create a node to represent the first number from other to multiply each number in this by
        // Use multOneDigit to multiply this by the first number in other for the first iteration
        int count = 0;
        Node multiplier = other.noderev.head;
        SuperMult = this.multOneDigit(multiplier.data, this);
        other.noderev.head = other.noderev.head.next;
        multiplier = other.noderev.head;
        other.noderev.length -= 1;
        count++;

        // For loop to multiply remaining digits
        for (int i = other.noderev.length; i > 0; i--) {
            temp = this.multOneDigit(multiplier.data, this);

            // Get the length of temp BigNum
            Node temporary = temp.noderev.head;
            temp.noderev.length = 0;
            while (temp.noderev.head != null) {
                temp.noderev.length++;
                temp.noderev.head = temp.noderev.head.next;
            }
            temp.noderev.head = temporary;

            // Add zeros based on number of iterations
            for (int j = count; j > 0; j--) {
                temp2.noderev.insert(0);
            }

            // Insert the product from temp BigNum to the temp2 BigNum
            for (int j = temp.noderev.length; j > 0; j--) {
                temp2.noderev.insert(temp.noderev.head.data);
                temp.noderev.head = temp.noderev.head.next;
            }
            SuperMult = SuperMult.add(temp2);
            other.noderev.head = other.noderev.head.next;
            multiplier = other.noderev.head;
            temp2.noderev.head = temp2.noderev.head.next;
            count++;
        }
        if (this.noderev.sign == "-" && other.noderev.sign == "-") {
            SuperMult.noderev.NumSign(1);
        }
        if (this.noderev.sign == "-" && other.noderev.sign != "-") {
            SuperMult.noderev.NumSign(-1);
        }
        if (this.noderev.sign != "-" && other.noderev.sign == "-") {
            SuperMult.noderev.NumSign(-1);
        }
        if (this.noderev.sign != "-" && other.noderev.sign != "-") {
            SuperMult.noderev.NumSign(1);
        }
        if (SuperMult.noderev.head.data == 0) {
            SuperMult.noderev.NumSign(1);
        }
        return SuperMult;
    }

    // Use this to test your other BigNum methods
    public static void main(String[] args) {
        //BigNum num1 = new BigNum("1234567898870972984749865732468572489759843757846892356846385634875439865874658394759864238746582974876856796853789467587364897598673958476038946758673598476983405768453675876953709867895347698435769854378687458976485767278547645353764564456735457625637452764567354672354675325476453764576235463548254783254523548867564786578345465346732543765436356783547836437486875376475378548635465876478546875628729654576457647825674385684756487654874567845674245684765874687464873645387425687426587462874623786548723648756438756438765428746578436258762875623487562873564378652387645");
        //BigNum num2 = new BigNum("49615216977536853639142237328710550701070912936318091260664443149767897638247575330563664289407178856061447598514199547429718019103328076528216583898191023762671313974496737374369216323780317216481231");
        //BigNum num3 = new BigNum(11111);
        BigNum num4 = new BigNum("1");
        BigNum num5 = new BigNum("1000000000000");
        //BigNum num6 = new BigNum(-654321);
        //BigNum num7 = new BigNum(654321);
        //BigNum num8 = new BigNum("100");
        //BigNum num9 = new BigNum(-123456789);
        //BigNum num10 = new BigNum("11111");
        //BigNum num11 = new BigNum("8934yrrdfjhd9234y");
        //String str1 = num4.toString();
        //String str2 = num2.toString();
        //String str3 = num6.toString();
        //System.out.println(str1);
        //System.out.println(str2);
        //System.out.println(num1.compareTo(num3));
        //System.out.println(num5.compareTo(num4));
        //System.out.println(num1.compareTo(num5));
        //System.out.println(num5.compareTo(num8));
        //System.out.println(num1.add(num4));
        //System.out.println(str3);
        //System.out.println(num4.add(num8));
        //System.out.println(num10.multOneDigit(3, num10));
        //System.out.println(num1.subtract(num2));
        //System.out.println(num7.subtract(num6));
        System.out.println(num5.subtract(num4));
        //System.out.println(num4.multiply(num2));
        //System.out.println(num1.multiply(num4));
    }
}

