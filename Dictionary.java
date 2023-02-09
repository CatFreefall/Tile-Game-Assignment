import java.util.LinkedList;

public class Dictionary implements DictionaryADT {

	private LinkedList<Record>[] hashTable;
	private int numRecords;	

	//This constructor creates a new 2D array (Hash table) of linked lists and puts a new empty linked list in every index
	public Dictionary(int size) {
		if (size < 5000) hashTable = new LinkedList[4999];
		else hashTable = new LinkedList[10007];
		numRecords = 0;
		
		for (int i = 0; i < hashTable.length; i ++) {
			hashTable[i] = new LinkedList();
		}
	}

	public int put(Record rec) throws DuplicatedKeyException {
		int index = hashFunction(rec.getKey(), hashTable.length);
		int returnValue;
		
		//Checking for a duplicated key and throwing the exception if it exists
		for (int i = 0; i < hashTable[index].size(); i ++) {
			if (hashTable[index].get(i).getKey() == rec.getKey()) throw new DuplicatedKeyException("Error. This key already exists.");
		}
		
		if (hashTable[index].size() == 0) {
			returnValue = 0;
			numRecords ++;
			hashTable[index].add(rec);
		}
		else {
			returnValue = 1;
			numRecords ++;
			hashTable[index].add(rec);
		}
		return returnValue;
	}

	public void remove(String key) throws InexistentKeyException {
		int index = hashFunction(key, hashTable.length);
		boolean keyExists = false;
		
		//Indexing through the contents of the linked list in the key's index until it is found and returns it. Else throw an exception.
		//Also subtracting numRecords accordingly
		if (hashTable[index] == null) keyExists = false;
		else {
			for (int i = 0; i < hashTable[index].size(); i ++) {
				if (hashTable[index].get(i).getKey().equals(key)) {
					numRecords --;
					hashTable[index].remove(i);
					keyExists = true;
				}
			}
		}
		if (keyExists == false) throw new InexistentKeyException("Error. This key does not exist.");
	}

	public Record get(String key) {
		int index = hashFunction(key, hashTable.length);
		Record returnValue = null;
		
		//Like the remove method, indexing through the contents of the linked list in the key's index until it is found and returns it. Else return null
		for (int i = 0; i < hashTable[index].size(); i ++) {
			if (hashTable[index].get(i).getKey().equals(key)) returnValue = hashTable[index].get(i);
		}
		return returnValue;
	}

	public int numRecords() {
		return numRecords;
	}
	
	/*
	 * This is my hash function. It converts every character in the key into ascii code. The
	 * ascii code is then multiplied by the position of the character twice relative to the key as a whole
	 * starting from 0 to the length of the key from left to right and is put into a new array of type
	 * integer. Lastly, all values in that array are added up and modulo'd by the size of the hash table.
	 */
	private int hashFunction(String key, int size) {
		int keyCharSize = 0;
		String[] keyArrayStr = new String[key.length()];
		int[] keyArrayInt = new int[key.length()];
		
		keyArrayStr = key.split("");
		
		for (int i = 0; i < keyArrayStr.length; i ++) {
			keyArrayInt[i] = keyArrayStr[i].charAt(0);
			keyArrayInt[i] = keyArrayInt[i] * i * i;
		}
		for (int i = 0; i < keyArrayInt.length; i ++) {
			keyCharSize += keyArrayInt[i];
		}
		return keyCharSize % (size);
	}
}