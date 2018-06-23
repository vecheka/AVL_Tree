
public class Main {

	public static void main(String[] args) {
		final AVLTree<Integer> test = new AVLTree<>();
//		test.insert(2);
//		test.insert(3);
//		test.insert(1);
//		test.insert(4);
//		test.insert(6);
		
		test.insert(9);
		test.insert(5);
		test.insert(10);
		test.insert(0);
		test.insert(6);
		test.insert(11);
		test.insert(-1);
		test.insert(1);
		test.insert(2);
		test.printPreOrder();
		
		test.remove(10);
		System.out.println();
		test.printPreOrder();
		
		test.insert(3);
		System.out.println();
		test.printPreOrder();
		
		
	}

} 
