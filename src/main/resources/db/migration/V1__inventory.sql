CREATE TABLE item (
                      item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      item_name VARCHAR(255) NOT NULL,
                      category VARCHAR(100),
                      fit VARCHAR(50),
                      description TEXT,
                      tags VARCHAR(255),
                      base_price DECIMAL(10, 2),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE item_variation (
                                variation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                item_id BIGINT,
                                sku VARCHAR(100) UNIQUE,
                                color VARCHAR(50),
                                size VARCHAR(50),
                                additional_price DECIMAL(10, 2) DEFAULT 0.00,
                                stock_quantity INT,
                                material VARCHAR(50) DEFAULT NULL,
                                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (item_id) REFERENCES item(item_id)
);

CREATE INDEX idx_item_id ON item_variation(item_id);
CREATE INDEX idx_color_size ON item_variation(color, size);