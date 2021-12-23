import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class assign3 {	
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("tree.txt");
		Scanner input = new Scanner(file);
		Scanner input2 = new Scanner(file);
		Scanner battery =  new Scanner(System.in);
		int batteryCapacity;
		double percentageExploration;
		ArrayList<String> listLines = new ArrayList<>();
		String[] elements;
		Node<String> node = null;		
		Node<String> root = null;			
		ArrayList<String> nodesVisited = new ArrayList<>();
		ArrayList<String> preOrderList = new ArrayList<>();
		ArrayList<String> DFSList = new ArrayList<>();		
		
				
		while(input.hasNextLine()) {			
			listLines.add(input.nextLine());			
		}		
		
		String firstLine = listLines.get(0);
		String[] elementsFirstLine = firstLine.split(" ");
		for(int i = 1; i<elementsFirstLine.length;i++) {
			if(root == null) {
				root = new Node<String>(null, elementsFirstLine[0]);
			}
			root.addChild(elementsFirstLine[i]);			
		}			
		
		
		for(int i=1; i<listLines.size(); i++) {
			elements = listLines.get(i).split(" ");	
			ArrayList<String> rootChildren = new ArrayList<String>();
			root.createTree(root, elements[0], rootChildren, root, node, elements);
		}				

		preOrderList = preOrder(root, preOrderList);
		
		
		System.out.println("Pre-order traversal: " + preOrderList.toString());	
		System.out.println("\nTree structure from file: ");
		printTree(input2);
		
		System.out.println("\nEnter Battery Capacity: ");
		batteryCapacity = battery.nextInt()/2;
		
		System.out.print("\nPath: ");
		DFSgenerator(root, DFSList, batteryCapacity, nodesVisited);
		System.out.println(root.value);
		
		percentageExploration = ((double)nodesVisited.size() / preOrderList.size()) * 100.0;		
		System.out.printf("Percentage of exploration: %.2f%s", percentageExploration, "%");
		
		input.close();
		input2.close();
		battery.close();
	}
	
	
	
	public static void printTree (Scanner s) {		
		
		while(s.hasNextLine()) {
			System.out.println(s.nextLine());
		}
		
		
	}
	
	static void printDFS(ArrayList<String> list, int battery, ArrayList<String> nodesVisited){      
		ArrayList<String> reverseList = new ArrayList<>();
		int size;
		if(battery >= list.size()) size = list.size();
		else size = battery + 1;
			
		for(int i = 0; i<size;i++) {			
			System.out.print(list.get(i) + "-");			
			reverseList.add(list.get(i));						
				
		}      
	   
        if(reverseList.size() > 2) {
        	for(int j = reverseList.size()-2; j>0; j--) {
            	System.out.print(reverseList.get(j) + "-");            	
        	}
        }
        
        for(int i=0; i<reverseList.size(); i++) {
        	if(!nodesVisited.contains(reverseList.get(i))) {
        		nodesVisited.add(reverseList.get(i));
        	}
        }        
        
        
    }
	
	static void DFSgenerator(Node<String> node, ArrayList<String> list, int battery, ArrayList<String> nodesVisited) {               
        if (node == null) return;         
        
        list.add(node.value);        
         
        if (!node.hasChildren()) {        	        	
        	printDFS(list, battery, nodesVisited);
        	list.remove(list.size() - 1);
            return;
        }
 
        for (int i = 0; i < node.getChildren().size(); i++) {
        	DFSgenerator(node.children.get(i), list, battery, nodesVisited);
        }
        list.remove(list.size() - 1);
    }	
	
	
	public static ArrayList<String> preOrder(Node<String> node, ArrayList<String> preOrderList) {		
		
		preOrderList.add(node.value);
		if(node.hasChildren()) {
			for(Node<String> child : node.children) {
				
				preOrder(child, preOrderList);
			}
		}		
				
		return preOrderList;
	}
	
	
	public static class Node<E> {
		private ArrayList<Node<E>> children;		
		private Node<E> parent;
		private E value;			
		
		
		public Node(Node<E> p, E value) {
			this.parent = p;
			this.value = value;
			this.children = new ArrayList<Node<E>>();
		}				

		
		public void addChild(E value) {
			if(this.children != null) {
				this.children.add(new Node<E>(this,value));
			}
		}
		
		
		public Node<E> getChildAt(int position) {
			if(position < this.children.size()) {
				return this.children.get(position);
			}
			return null;
		}		
		
		
		public ArrayList<E> getChildren() {
			ArrayList<E> listChildren = new ArrayList<>();
			for(int i= 0; i<children.size(); i++) {
				E child =children.get(i).value;
				listChildren.add(child);
			}
			
			return listChildren;
		}	

		public Node<E> createTree(Node<E> node, E value, ArrayList<E> children, Node<E> root, Node<E> nodeCurr,E[] elements) {
			
			if(node.getChildren().contains(value) || node.value == value) {		
				children = node.getChildren();	
				for(int j = 1; j<elements.length;j++) {	
					if(children.contains(elements[0])) {
						int index = children.indexOf(elements[0]);
						nodeCurr = node.getChildAt(index);
						nodeCurr.addChild(elements[j]);					
					} 
					
				}			
			return root;
			}

			for(Node<E> child : node.children) {				
				createTree(child,value, children, root, nodeCurr, elements);				
				
			}					
			
			return root;
		}		
		
		public boolean hasChildren() {
			if(this.children != null) {
				return (this.children.size() != 0);
			}			
			return false;
		}
		
		public String toString() {
			return "" + this.value;
		}
		
		
	}
	
	
	
}
