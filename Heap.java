import java.util.ArrayList;
import java.util.HashMap;

public class Heap<T extends Comparable<T>> {
    ArrayList<T> data = new ArrayList<>();
    HashMap<T, Integer> map = new HashMap<>();

    public void add(T item) {
        data.add(item);
        map.put(item, this.data.size() - 1);
        upheapify(data.size() - 1);
    }

    private void upheapify(int ci) {
        int pi = (ci - 1) / 2;
        if (ci > 0 && isLarger(data.get(ci), data.get(pi)) > 0) {
            swap(pi, ci);
            upheapify(pi);
        }
    }

    private void swap(int i, int j) {
        T ith = data.get(i);
        T jth = data.get(j);
        
        data.set(i, jth);
        data.set(j, ith);
        map.put(ith, j);
        map.put(jth, i);
    }

    public void display() {
        System.out.println(data);
    }

    public int size() {
        return this.data.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public T remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        swap(0, this.data.size() - 1);
        T rv = this.data.remove(this.data.size() - 1);
        map.remove(rv);
        if (!isEmpty()) {
            downheapify(0);
        }
        return rv;
    }

    private void downheapify(int pi) {
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        int mini = pi;

        if (lci < this.data.size() && isLarger(data.get(lci), data.get(mini)) > 0) {
            mini = lci;
        }
        
        if (rci < this.data.size() && isLarger(data.get(rci), data.get(mini)) > 0) {
            mini = rci;
        }
        
        if (mini != pi) {
            swap(mini, pi);
            downheapify(mini);
        }
    }

    public T get() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return this.data.get(0);
    }

    public int isLarger(T t, T o) {
        return t.compareTo(o);
    }

    public void updatePriority(T item) {
        if (!map.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in the heap");
        }
        int index = map.get(item);
        upheapify(index);
        downheapify(index); // Handle the case where the item's priority might need to be adjusted down
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>();

        heap.add(10);
        heap.add(20);
        heap.add(5);
        heap.add(30);

        System.out.println("Heap after adding elements:");
        heap.display(); // Should display [30, 20, 5, 10] if it's a max-heap

        System.out.println("Removed element: " + heap.remove()); // Should remove the max element (30)

        System.out.println("Heap after removing the max element:");
        heap.display(); // Should display the remaining elements
    }
}
