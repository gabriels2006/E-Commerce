import java.time.LocalDate;

public class Product {

    private String name;
    private String description;
    private float price;
    private int quantityInStock;
    private boolean active;
    private String category;
    private String brand;
    private String model;
    private String manufacturer;
    private LocalDate manufactureDate;
    private float discount;

    //Construtor de Produto Completo
    public Product(String name, String description, float price, int quantityInStock, String category, String brand, String model, String manufacturer, LocalDate manufactureDate) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
            }
            if (price <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero.");
            }
            if (quantityInStock < 0) {
                throw new IllegalArgumentException("Estoque não pode ser negativo.");
            }

            this.name = name;
            this.description = description;
            this.price = price;
            this.quantityInStock = quantityInStock;
            this.active = true;
            this.category = category;
            this.brand = brand;
            this.model = model;
            this.manufacturer = manufacturer;
            this.manufactureDate = manufactureDate;
            this.discount = 0;

    }

    // Reduzir estoque
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        if (quantity > this.quantityInStock) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        this.quantityInStock -= quantity;
    }

    public void setDiscount(float discount) {
        if (discount < 0 || discount > 100) throw new IllegalArgumentException("Percentual inválido.");
        this.discount = discount;
    }

    public float getFinalPrice() {
        return price - (price * (discount / 100));
    }

    // Metodo para verificar disponibilidade (Co-validar com estoque)
    public boolean isAvailable() {
        return active && quantityInStock > 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDate getManufactureDate() {
        return this.manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", manufactureDate=" + manufactureDate +
                '}';
    }

}
