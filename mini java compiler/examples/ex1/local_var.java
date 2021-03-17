class Main {
    public static void main(String[] args) {
        System.out.println(new Example().run());
    }
} 

class Example {
	public int run() {
		int x;
		x = 0;
		return x + x;
	}

	public int other() {
		int x;
		x = 1;
		return x - 1;
	}
}