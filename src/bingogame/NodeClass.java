/**
 *
 * @author Furkan AK @Kowachka
 */

package bingogame;

public class NodeClass<T> {

	T data; // Data stored in the node.
	boolean marked = false; // Indicates whether the node is marked or not.
	NodeClass<T> next; // Pointer to the next node.
	NodeClass<T> down; // Pointer to the child node (for a multi linked list).

	public NodeClass(T data) {
		this.data = data;
		this.next = null;
		this.down = null;
	}

}