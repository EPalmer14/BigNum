public class BigNum {

    private class Node {
        private Node head;
        String number;
        int length;
        int data;
        Node next;

        private void push(int data) {
            Node temp = new Node();
            temp.data = data;
            temp.next = head;
            head = temp;
        }

        private void insert(int data) {
            Node node = new Node();
            node.data = data;
            // node.next = null;

            if (head == null) {
                head = node;
            } else {
                Node n = head;
                while (n.next != null) {
                    n = n.next;
                }
                n.next = node;
            }
        }

        public void show() {
            Node node = head;
            while (node.next != null) {
                // System.out.println(node.data);
                node = node.next;
            }
            // System.out.println(node.data);
        }

        private String printStack2() {
            String a = "";
            Node temp = head;
            while (temp != null) {
                a += temp.data;
                temp = temp.next;
            }
            String b = "";
            for (int i = a.length(); i > 0; i--) {
                b += a.substring(i - 1, i);
            }
            // System.out.println(b);
            return b;
        }


        private void printStack() {
            Node temp = head;
            while (temp != null) {
                // System.out.print(temp.data + " --> ");
                temp = temp.next;
            }
        }
    }

    private Node test;
    private Node build;


    // Construct an empty BigNum.
    public BigNum() {
        test = new Node();
        build = new Node();
    }

    // Construct a BigNum from an int, which must be >= 0. Throw an
    // IllegalArgumentException if n < 0.
    public BigNum(int n) {
        test = new Node();
        build = new Node();
        if (n < 0) {
            throw new IllegalArgumentException("Error: Must be greater than or equal to 0");
        } else {
            String a = Integer.toString(n);
            int j = 0; // 123456 --> 6 5 4 3 2 1
            for (int i = a.length(); i > 0; i--) {//O(log N)
                int i1 = Integer.parseInt(a.substring(i - 1, i));
                test.insert(i1);//O(log N)
                test.length++;
                // ___________________________________________
                // 123456 --> 1 2 3 4 5 6
                int i2 = Integer.parseInt(a.substring(j, j + 1));
                build.insert(i2);
                // System.out.println(i2);
                j++;
            }
        }
        // test.show();
    }

    // Construct a BigNum from a String, which must contain only
    // characters between 0 and 9. Throw an IllegalArgumentException
    // for any other character.
    public BigNum(String s) {//s="0987"
        if (s.length() >= 40000) {
            throw new IllegalArgumentException("Error: Length must be less than 40,000");
        }
        if (!s.matches("[0-9]+")) {
            throw new IllegalArgumentException("Not an integer");
        }
        int i3 = 0;
        while (s.charAt(i3) == '0') {
            i3++;
        }
        s = s.substring(i3);
        test = new Node();
        build = new Node();
        int j = 0;
        for (int i = s.length(); i > 0; i--) {
            int i1 = Integer.parseInt(s.substring(i - 1, i));
            if (i1 >= 0 && i1 <= 9) {
                test.insert(i1);
                test.length++;
                //___________________________________________________
                int i2 = Integer.parseInt(s.substring(j, j + 1));
                build.insert(i2);
                // System.out.println(i2);
                j++;
            } else {
                throw new IllegalArgumentException("Error: Must be between 0 and 9.");
            }
        }
        // test.show();
    }

    // Convert this BigNum to a String
    public String toString() {
        return test.printStack2();
    }

    // Compare this BigNum to another BigNum, returning 0 if they are equal,
    // a value > 0 if this > other, or a value < 0 if this < other.
    public int compareTo(BigNum other) {
        if (this.test.length > other.test.length) {
            return 1;
        }
        if (other.test.length > this.test.length) {
            return -1;
        }
        while (this.build.head.next != null) {
            int i1 = this.build.head.data;
            int i2 = other.build.head.data;
            this.build.head = this.build.head.next;
            other.build.head = other.build.head.next;
            if (i1 > i2) {
                return 1;
            }
            if (i2 > i1) {
                return -1;
            }
        }
        return 0;
    }

    // Add this BigNum to another BigNum, returning a new BigNum which contains the sum of the two
    public BigNum add(BigNum other) {
        // initialize the new BigNum
        BigNum sum = new BigNum();
        int i1;
        int carry = 0;
        i1 = this.test.head.data + other.test.head.data + carry;
        sum.test.insert(i1 % 10);
        carry = i1 / 10;
        while (this.test.head.next != null && other.test.head.next != null) {
            this.test.head = this.test.head.next;
            other.test.head = other.test.head.next;
            i1 = this.test.head.data + other.test.head.data + carry;
            sum.test.insert(i1 % 10);
            carry = i1 / 10;
        }
        if (this.test.head.next != null) {
            while (this.test.head.next != null) {
                this.test.head = this.test.head.next;
                i1 = this.test.head.data + carry;
                sum.test.insert(i1 % 10);
                carry = i1 / 10;
            }
        } else { // if other.test.head.next != null && this.test.head.next == null
            while (other.test.head.next != null) {
                other.test.head = other.test.head.next;
                i1 = other.test.head.data + carry;
                sum.test.insert(i1 % 10);
                carry = i1 / 10;
            }
        }
        if (carry == 1) {
            sum.test.insert(carry);
        }
        sum.test.printStack2();
        return sum;
    }

    // Use this to test your other BigNum methods
    public static void main(String[] args) {
        BigNum mark1 = new BigNum("3438295235894579245");
        BigNum mark2 = new BigNum("9000000000000000000");
        // System.out.println(mark1.toString());
        System.out.println(mark1.compareTo(mark2));
    }
}
