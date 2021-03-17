class Main {
    public static void main(String[] args) {
        System.out.println(new Example().run());
    }
} 

class Example {	
	public int run() {
		Example e;
		e = new Example();
		return e.run();
	}
}

class NonExample {
	public int run() {
		return new NonExample().run();
	}
}