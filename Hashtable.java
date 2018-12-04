
public class Hashtable {

	private static class Node {
		String key;
		String value;
		Node next;
	}

	private Node[] table;
	private int count;

	public Hashtable() {
		table = new Node[64];
	}

	public Hashtable(int initialSize) {
		if (initialSize <= 0) {
			System.out.print("You entered a wrong value for the table. ");
		}
		table = new Node[initialSize];
	}

	private int hash(Object key) {
		return (Math.abs(key.hashCode())) % table.length;
	}

	public void put(String key, String value) {

		int bucket = hash(key);
		Node list = table[bucket];
		while (list != null) {
			if (list.key.equals(key))
				break;
			list = list.next;
		}

		if (list != null) {
			list.value = value;
		} else {
			if (count >= 0.75 * table.length) {
				grow();
				bucket = hash(key);
			}
			Node newNode = new Node();
			newNode.key = key;
			newNode.value = value;
			newNode.next = table[bucket];
			table[bucket] = newNode;
			count++;
		}
	}

	public String get(String key) {

		int bucket = hash(key);
		Node list = table[bucket];

		while (list != null) {
			if (list.key.equals(key))
				return list.value;
			list = list.next;
		}

		return null;
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String remove(String key) {

		int bucket = hash(key);

		Node node = table[bucket];

		Node prev = null;

		while (node != null) {
			if (node.key == key) {
				break;
			}

			prev = node;
			node = node.next;
		}

		if (node == null) {
			return null;
		}

		--count;

		if (prev == null) {
			table[bucket] = table[bucket].next;

		} else {
			prev.next = node.next;
		}

		return node.value;
	}

	public boolean containsKey(String key) {

		int bucket = hash(key);
		Node list = table[bucket];
		while (list != null) {
			if (list.key.equals(key))
				return true;
			list = list.next;
		}

		return false;
	}

	private void grow() {
		Node[] temp = new Node[table.length * 2];
		for (int i = 0; i < table.length; i++) {
			Node list = table[i];
			while (list != null) {
				Node next = list.next;
				int hash = (Math.abs(list.key.hashCode())) % temp.length;
				list.next = temp[hash];
				temp[hash] = list;
				list = next;
			}
		}
		table = temp;
	}
}
