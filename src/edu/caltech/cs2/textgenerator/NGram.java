package edu.caltech.cs2.textgenerator;

import edu.caltech.cs2.datastructures.IterableString;
//import edu.caltech.cs2.datastructures.LinkedDeque;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.ArrayDeque;
import java.util.Iterator;
public class NGram implements Iterable<String>, Comparable<NGram> {
    public static final String NO_SPACE_BEFORE = ",?!.-,:'";
    public static final String NO_SPACE_AFTER = "-'><=";
    public static final String REGEX_TO_FILTER = "”|\"|“|\\(|\\)|\\*";
    public static final String DELIMITER = "\\s+|\\s*\\b\\s*";
    private IDeque<String> data;

    public static String normalize(String s) {
        return s.replaceAll(REGEX_TO_FILTER, "").strip();
    }

    public NGram(IDeque<String> x) {
        this.data = new IterableString.LinkedDeque<>();
        for (String word : x) {
            this.data.add(word);
        }
    }

    public NGram(String data) {
        this(normalize(data).split(DELIMITER));
    }

    public NGram(String[] data) {
        this.data = new IterableString.LinkedDeque<>();
        for (String s : data) {
            s = normalize(s);
            if (!s.isEmpty()) {
                this.data.addBack(s);
            }
        }
    }

    public NGram next(String word) {
        String[] data = new String[this.data.size()];
        Iterator<String> dataIterator = this.data.iterator();
        dataIterator.next();
        for (int i = 0; i < data.length - 1; i++) {
            data[i] = dataIterator.next();
        }
        data[data.length - 1] = word;
        return new NGram(data);
    }

    public String toString() {
        String result = "";
        String prev = "";
        for (String s : this.data) {
            result += ((NO_SPACE_AFTER.contains(prev) || NO_SPACE_BEFORE.contains(s) || result.isEmpty()) ? "" : " ") + s;
            prev = s;
        }
        return result.strip();
    }

    @Override
    public Iterator<String> iterator() {
        return this.data.iterator();
    }

    @Override
    public int compareTo(NGram other) {
        //return an integer value indiciating the relative ordering of the 2 objects

        // Compare sizes first
        int sizeDiff = this.data.size() - other.data.size();
        if (sizeDiff != 0) {
            //if calling object is bigger than passed ngram, immediatly return the size difference
            return sizeDiff;
        }

        // Sizes are the same, so compare element by element
        Iterator<String> thisIter = this.iterator();
        Iterator<String> otherIter = other.iterator();
        //int index = 0;
        while (thisIter.hasNext() && otherIter.hasNext()) {

            String thisElement = thisIter.next();
            String otherElement = otherIter.next();
            int elementDiff = thisElement.compareTo(otherElement);
            if (elementDiff != 0) {
                return elementDiff;
            }
        }
        // If we got here, the n-grams are equal
       return 0;

    }

    @Override
    public boolean equals(Object o) {
        //the calling object is "this", and it is implied as the first object being comapred for
        //equality.
        //check if the input object is an instance of the Ngram class
        if (!(o instanceof NGram)) {
            return false;
        }
        NGram other = (NGram) o;
//        if(o.hashCode() != other.hashCode()){
//            return false;
//        }
//        return true;
        //compare the sizes of internal data field of the two Ngrams
        if (this.data.size() != other.data.size()) {
            return false;
        }
        Iterator<String> thisIterator = this.data.iterator();
        Iterator<String> otherIter = other.data.iterator();
//        //loop thorugh both data fields and compare the elements one by one
        while (thisIterator.hasNext()) {
            String thiswordNext = thisIterator.next();
            String otherWordNext = otherIter.next();
            //recursively check each word
            if (!thiswordNext.equals(otherWordNext)) {
                return false;
            }

        }
        //if never breaks, then true
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        int prime = 1;
        //previous error: not changing prime resulted in collision after a certain number of "re-entries". updating prime for each element resolves this
        for (String e : this.data) {
            if (e == null) {
                hashCode = 31 * hashCode;
            } else {
                hashCode = 31 * hashCode + e.hashCode();
                prime = prime * 31;
            }
           // hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());

        }
        return hashCode;
    }
}
//hashfunction method for computing table index from key
        //if table[i].equals(x)
        //
        //int hash = 0;
        //Iterator<String> Iter = this.data.iterator();
      //  NGram hash = (NGram) Iter.next();
//
//        while (Iter.hasNext()) {
//            hash += ((31 * hash) + Iter.next().hashCode());
//        }
//        else{
//            int skip = this.data.size() / (16*1/2);
//            for(int i =0; i< this.data.size(); i += skip){
//                hash = Integer.parseInt((31 * hash) + data.removeFront());
//            }
//            return hash;
//        }
//        return hash;
//    }

//        int hash = 1;
//        if (this.data.size() < 16) {
//            // for (int i = 0; i < this.data.size(); i++) {
//            while (Iter.hasNext()) {
//                hash = ((31 * hash) + Iter.next().hashCode());
//            }
//        } else {
//            int skip = this.data.size() / (16 * 1 / 2);
//            for (int i = 0; i < this.data.size(); i += skip) {
//                hash = ((31 * hash) + next(         )Iter.next().hashCode());
//            }
//
//        }
//        return hash;
//    }


        //random, but things like count should not be parameters
        // create an array of a specific size to store the buckets
        //make it the size of ideque?
        //create hashcode function
        // if size is a specific size (not bigger than this)
        //apply (here is the recursive call) hashcode function
        //get hashindex
        //if array hashindex is empty, put element there
        //if is not empty
        //check linked list and see if linked list contains the element
        //if not, add element to linked list
        //for remove last-in first out pop







//designing a hashset
//public class MyHashSet{
//public void add(int key){
//int index = key % ARRAY_SIZE;
//List<Integer> childList = parentList.get(index);
//if(childList == null) {
//  List<Integer> list = new LinkedList<>();
// list.add(key)
//parentList.set(index, list);
//} else{
// if(!childList.contains(key)){
// childList.add(key);
//}
//public void remove(int key){
// int index = key % ARRAY_SIZE;
//List<Integer> childList = parentList.get(index);
//if (childList != null) {
//remove integer object NOT place. remove the thing that you are passing in
// childList.remove(Integer.valueOf(key));
//}
//public boolean contains(int key){
//int index = key % ARRAY_SIZE;
//List<Integer> childList = parentList.get(index);
//return childList != null && childList.contains(key);
//using methods on themselves

//counting n-grams
// os = 5 (when individual)
// n# of grams
// fs = os - n + 1
// fs = 5 - 4 + 1 = 2
//public static Map<String, Integer> calculateNGrams(String inputString, int n){
// Map<String, Integer> finalOutput = new HashMap<>();
//String [] singleWords = inputString.split(" ");
// int nNumber = singleWords.length - n + 1;
//for (int i =0; i < nNumber; i++){
// StringBuilder tmp = new StringBuilder();
// for (int j = 0; j < n; j++){
// if (i > 0) tmp.append(" ")
//tmp.append(singleWords[i+j])
//if (finalOutput.containsKey(tmp.toString())

//finalOutput.put(tmp.toString(), finalOutput.get(tmp.toString()+1)
//else{
// finalOutput.put(temp.toString(), 1)
//
//return finalOutput;

//public static void printNgrams(Map<String, Integer> ngrams){
// for(Map.Entry<String, Integer> entry: nGrams.EntrySet()){
//System.out.println(entry.getKey() + " " + entry.getValue() + "\n");


//in main
//printNgrams(CalculateNgrams(inputString, 1))


//probing (closed hashing)
//linear probing:
//if something is already at element
//stride by 1 to find next unused array entry
//sucky so just double hash
//if empty, then apply a second hash function
//cuckoo hashing-> 2 hash functions are used
// each element is stored at one of the 2 locations computed by these hash functions
//at most 2 table locations must be consulted to determine whether the elemnt is present
//if both possible locations are occuped, the newly added element displaces
//the old element that was there and this element is re-added to the table
//a chain of displacements occurs

//TL:DR: a hash table size that is a prime number is the only way to gurantee tha tyou do not accidently re-probe,
//a prviosuly probed location
//Steps:
//hashval1 is used for the intial insertion probe
//if that location is empty, then you insert the (k,v) and you are done never using hashval2
//if that location is occupied, the you jump a hashval2 address to (hashval1+ hashval2) % arraysize
//if this location is not empty jump another hashval2 adresses to (hashval1 + 2*hashval2) % arraysize
// if hashval2 =2 (as opposed to another hashfunction), you miss all odd or even numbers and you probe these slots twice, which is pointless
//the problem here is that the hash table size and hashval2 share common factos. So we need the hash table szie and hashval2
//to share no factors. Since hashval2 is a function of k and post module lies in the range of 0<hashval2< arraysize, there
// is nothing we can do about hashval2 (i.e hashval2 is dependent on arraysize)
//thath leaves arraysize. Must be gruanteeded to have no factors in common with hashval2, thus
//by simple math, the arraysize must be a prime number
//make tablesize a varible
//IF YOU FIND AN INSERTION INTO THE TABLE REQUIRES MORE THAN A SPECIFIED NUMBER OF PROBES, SINGIFYING CLSUTERING.
// n/m average of elements per bucket (load factor O(1 +n/m) is a good has function
// if we can ensure that the load factor a = n/m neever exceeds some fixed value a max, then all operations take
// O( 1 + amaz) = O(1) time on average. if random this is the worst case
//optimal  bounds, best if a is within a narrow range
//if a less than 1/2, array is sparse (downsize) if a is 2, cost of traversing linked lists limits performance
//H1(KEY) = KEY%B
//mod  K % 2
//second hash function in range Max(1, TableSize-1)
//prime table size B
//Porbe increment == H2(KEy) = Max(1, (Key%B))
//double hashing
//in this method we use another fucntion to choose the probe increment, so different keys probe
//different amounts to spread the data around the hash table
//for example if we have hash function H1(Key) = Key % B
//where B is the number of entries in the table, we can also define a second hash function H2(Key)
//that defines a probe increment:
//H2(Key) = Probe Increment
//So depending upon the key, we probe a different amount each time to spread
//the data around if their is a collision
//does the probe increment gurantee that we will eventually find an open spot?
//if the probe incrment and the tbale size are relatively prome
//they share no common divisors other than 1, then you can prove that the probe sequence
//will in fact cover the entrie table.
//By choosing a table size B that is a prime number, and choosing a probe incrment that is mod B, we gurantee a useful and complete probe sequence.






//oTHER MEthods of generating a hashFunction
//Folding: take a multiple digit key and break it into groups of digits, and then treat these groups as the entities to be used in calculating the index. If the key is
//123456789, break it into 3 groups of 3 digit numbers 123-456-789 and add them up,
//or possibly mupltply each group by a base number
//add the hashcodes of the 3 keys up and use this to map to a slot
//with new string, get the first 3 words (which serve as key), check if values contain
//the last word


//first 2 words--> add, get hashcode, check if third word is a value
// 2nd and third word --> add get, hashcode check if fourth word is a value
//if i is last word
//do one more time then terminate
