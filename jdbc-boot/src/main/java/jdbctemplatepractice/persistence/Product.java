/**
 * Project: JDBCTemplate Practice
 * Description: A simple POJO to model domain entity product. Products contain a unique user id that acts as the primary
 *              key, a product name, a product description, a retail price and a vendor price.
 * Author: Benjamin Soto-Roberts
 * Created: 03/03/2026
 */

package jdbctemplatepractice.persistence;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID uuid;
    private String productName;
    private String productDesc;
    private BigDecimal retailPrice;
    private BigDecimal vendorPrice;

    public Product() {
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(BigDecimal vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid='" + uuid + '\'' +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", retailPrice=" + retailPrice +
                ", vendorPrice=" + vendorPrice +
                '}';
    }
}
