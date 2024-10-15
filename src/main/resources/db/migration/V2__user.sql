CREATE TABLE user (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      first_name VARCHAR(50),
                      last_name VARCHAR(50),
                      email VARCHAR(255) UNIQUE NOT NULL,
                      phone VARCHAR(15),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_phone ON user(phone);

CREATE TABLE purchase_history (
                                  purchase_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  user_id BIGINT,
                                  variation_id BIGINT NOT NULL,
                                  quantity INT,
                                  purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  total_price DECIMAL(10, 2),
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (user_id) REFERENCES user(user_id),
                                  FOREIGN KEY (variation_id) REFERENCES item_variation(variation_id)
);

CREATE INDEX idx_user_id_history ON purchase_history(user_id);
CREATE INDEX idx_item_id ON purchase_history(variation_id);
