package um.edu.uy.List;

public class List<K> {
    public ListNode head;

    public List<K> crearNuevaLista() {
        List<K> nueva = new List<K>();
        ListNode nuevoHead = null;
        ListNode nuevoActual = null;

        ListNode actual = this.head;

        while (actual != null) {
            if (actual.item >= 0) {
                ListNode nuevoNodo = new ListNode();
                nuevoNodo.item = actual.item;

                if (nuevoHead == null) {
                    nuevoHead = nuevoNodo;
                    nuevoActual = nuevoNodo;
                } else {
                    nuevoActual.next = nuevoNodo;
                    nuevoActual = nuevoNodo;
                }
            }
            actual = actual.next;
        }

        nueva.head = nuevoHead;
        return nueva;
    }

    public void eliminarNodosPositivos() {

        while (head != null && head.item >= 0) {
            head = head.next;
        }

        ListNode actual = head;

        while (actual != null && actual.next != null) {
            if (actual.next.item >= 0) {
                actual.next = actual.next.next;
            } else {
                actual = actual.next;
            }
        }
    }
    public void imprimir() {
        ListNode actual = head;
        while (actual != null) {
            System.out.print(actual.item + " ");
            actual = actual.next;
        }
        System.out.println();
    }

}