package il.cshaifasweng.OCSFMediatorExample.entities;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int storeId;

    public Catalog(int storeId) {
        this.storeId = storeId;
    }

    @OneToMany(mappedBy = "catalog")
    private List<Item> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setItems(List<Item> items){ this.items = items;}
    public void addItem(Item item){
        this.items.add(item);
    }

}
