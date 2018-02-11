package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc
	 *            Scanner from which a polynomial is to be read
	 * @throws IOException
	 *             If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// return null;

		// If there's nothing else to add, just give whatever's left

		if (poly1 == null) {

			return poly2;

		}

		if (poly2 == null) {

			return poly1;

		}

		/*
		 * Since the linked list has to be in ascending order by degree, the bigger one
		 * has to be at the end.
		 * 
		 */

		if (poly1.term.degree > poly2.term.degree) {

			Node c = poly2;

			for (Node i = poly2; i != null; i = i.next) { // Searching for a like term

				if (i.term.degree == poly1.term.degree) { // Found it!

					Node a = add(poly1.next, i.next); // Add up the rest of the polynomial
					Node b = new Node(i.term.coeff + poly1.term.coeff, i.term.degree, a);
					Node p = c;
					// "Inserting"
					while (p.next != i) {

						p = p.next;

					}

					p.next = b;

					return c;

				}

			}

			// Couldn't find a like term

			Node a = add(poly1.next, poly2);
			Node pointer = a;

			Node n = new Node(poly1.term.coeff, poly1.term.degree, null);

			while (pointer.next != null) {

				if (pointer.next.term.degree > n.term.degree) {

					Node temp = pointer.next;
					n.next = temp;
					pointer.next = n;

					return a;

				}

				pointer = pointer.next;

			}

			pointer.next = n;

			return a;

		}

		if (poly2.term.degree > poly1.term.degree) { // Same as above, just reversed

			Node c = poly1;

			for (Node i = poly1.next; i != null; i = i.next) {

				if (i.term.degree == poly2.term.degree) {

					Node a = add(poly2.next, i.next);
					Node b = new Node(i.term.coeff + poly2.term.coeff, i.term.degree, a);
					Node p = c;

					while (p.next != i) {

						p = p.next;

					}

					p.next = b;

					return c;

				}

			}

			Node a = add(poly2.next, poly1);
			Node pointer = a;

			Node n = new Node(poly2.term.coeff, poly2.term.degree, null);

			while (pointer.next != null) {

				if (pointer.next.term.degree > n.term.degree) {

					Node temp = pointer.next;
					n.next = temp;
					pointer.next = n;

					return a;
				}

				pointer = pointer.next;

			}

			pointer.next = n;

			return a;

		}
		/*
		 * Past this point means that the two heads of the list compared have the same
		 * degree and must be added
		 * 
		 */

		if (poly1.term.coeff + poly2.term.coeff == 0) { // Account for zeroes

			Node z = add(poly1.next, poly2.next);
			return z;

		}

		Node sum = new Node(poly1.term.coeff + poly2.term.coeff, poly1.term.degree, add(poly1.next, poly2.next));
		return sum;

	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// return null;

		Node product = null;

		// If there's nothing else to multiply with just move on

		if (poly1 == null) {

			return product;

		}

		if (poly2 == null) {

			return product;

		}

		Node b = product;
		
		// Go through both polynomials and multiply + add them together to get the product

		for (Node i = poly1; i != null; i = i.next) {

			for (Node j = poly2; j != null; j = j.next) {

				b = new Node(i.term.coeff * j.term.coeff, i.term.degree + j.term.degree, null);
				product = Polynomial.add(product, b);

			}

		}

		return product;

	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly
	 *            Polynomial (front of linked list) to be evaluated
	 * @param x
	 *            Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		if (poly == null) { // If there's nothing to evaluate

			return 0;

		}

		float sum = ((float) Math.pow(x, poly.term.degree) * poly.term.coeff) + evaluate(poly.next, x); // Evaluate and keep going

		return sum;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly
	 *            Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
