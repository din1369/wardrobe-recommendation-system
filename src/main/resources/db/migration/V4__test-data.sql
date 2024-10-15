-- Insert Users
INSERT INTO user (first_name, last_name, email, phone) VALUES
                                                           ('John', 'Doe', 'john.doe@example.com', '1234567890'),
                                                           ('Jane', 'Smith', 'jane.smith@example.com', '0987654321');

-- Insert Items
INSERT INTO item (item_name, category, fit, description, tags, base_price) VALUES
                                                                               ('Classic Jacket', 'JACKETS', 'MALE', 'A classic mens jacket', 'classic, jacket, men', 79.99),
                                                                               ('Casual Shirt', 'SHIRTS', 'MALE', 'Casual mens shirt', 'shirt, casual, men', 29.99),
                                                                               ('Formal Trousers', 'TROUSERS', 'MALE', 'Formal trousers for office wear', 'trousers, formal, office', 49.99),
                                                                               ('Leather Shoes', 'SHOES', 'MALE', 'Leather shoes for formal events', 'shoes, leather, formal', 99.99),
                                                                               ('Summer Dress', 'DRESS', 'FEMALE', 'A stylish summer dress', 'dress, summer, women', 59.99),
                                                                               ('Evening Gown', 'DRESS', 'FEMALE', 'Elegant evening gown for parties', 'gown, evening, women', 199.99),
                                                                               ('Unisex T-shirt', 'CASUAL', 'UNISEX', 'A comfortable unisex t-shirt', 't-shirt, casual, unisex', 19.99),
                                                                               ('Casual Sneakers', 'SHOES', 'UNISEX', 'Unisex sneakers for casual wear', 'sneakers, casual, unisex', 49.99),
                                                                               ('Winter Jacket', 'JACKETS', 'UNISEX', 'Warm winter jacket for cold weather', 'jacket, winter, unisex', 129.99),
                                                                               ('Jeans', 'TROUSERS', 'UNISEX', 'Classic unisex jeans', 'jeans, unisex, casual', 39.99),
                                                                               ('Christmas Sweater', 'JACKETS', 'UNISEX', 'A warm and cozy sweater for Christmas celebrations.', 'christmas, warm, cozy', 140.00),
                                                                               ('Party Dress', 'DRESS', '', 'An elegant dress for evening parties.', 'party, elegant, evening',499.99),
                                                                               ('Christmas Tree Decorations', 'DECOR', 'UNISEX', 'Beautiful decorations for your Christmas tree.','christmas, decorations', 199.99),
                                                                               ('Party Shoes', 'SHOES', 'UNISEX', 'Stylish and comfortable shoes for parties.','party, stylish, comfortable', 399.99),
                                                                               ('New Year Party Hat', 'ACCESSORIES', 'UNISEX', 'A fun hat to wear for New Year celebrations.','party, new year, fun', 99.99);

-- Insert 100 Item Variations
INSERT INTO item_variation (item_id, sku, color, size, additional_price, stock_quantity, material) VALUES
                                                                                                       (1, 'JCKT-001', 'Black', 'M', 0.00, 100, 'Cotton'),
                                                                                                       (1, 'JCKT-002', 'Blue', 'L', 5.00, 80, 'Wool'),
                                                                                                       (1, 'JCKT-003', 'Green', 'XL', 7.00, 60, 'Polyester'),
                                                                                                       (2, 'SHRT-001', 'White', 'M', 0.00, 120, 'Cotton'),
                                                                                                       (2, 'SHRT-002', 'Black', 'L', 2.00, 90, 'Cotton'),
                                                                                                       (2, 'SHRT-003', 'Gray', 'XL', 3.00, 75, 'Linen'),
                                                                                                       (3, 'TRSR-001', 'Black', '32', 0.00, 100, 'Wool'),
                                                                                                       (3, 'TRSR-002', 'Navy', '34', 5.00, 85, 'Polyester'),
                                                                                                       (3, 'TRSR-003', 'Gray', '36', 7.00, 70, 'Cotton'),
                                                                                                       (4, 'SHOE-001', 'Brown', '9', 0.00, 50, 'Leather'),
                                                                                                       (4, 'SHOE-002', 'Black', '10', 10.00, 40, 'Leather'),
                                                                                                       (5, 'DRESS-001', 'Red', 'S', 0.00, 60, 'Silk'),
                                                                                                       (5, 'DRESS-002', 'Blue', 'M', 10.00, 50, 'Silk'),
                                                                                                       (6, 'GOWN-001', 'Black', 'L', 0.00, 40, 'Satin'),
                                                                                                       (7, 'TSHRT-001', 'White', 'M', 0.00, 200, 'Cotton'),
                                                                                                       (7, 'TSHRT-002', 'Black', 'L', 5.00, 150, 'Cotton'),
                                                                                                       (8, 'SNEAK-001', 'White', '42', 0.00, 100, 'Canvas'),
                                                                                                       (8, 'SNEAK-002', 'Black', '43', 5.00, 80, 'Leather'),
                                                                                                       (9, 'JCKT-004', 'Red', 'M', 10.00, 100, 'Nylon'),
                                                                                                       (10, 'JEANS-001', 'Blue', '32', 0.00, 100, 'Denim'),
                                                                                                       (10, 'JEANS-002', 'Black', '34', 5.00, 90, 'Denim'),
                                                                                                       (3, 'TRSR-030', 'Brown', '36', 8.00, 50, 'Cotton');

INSERT INTO purchase_history (user_id, variation_id, quantity, total_price) VALUES
                                                                                (1, 1, 1, 79.99),  -- John Doe purchased a Classic Jacket
                                                                                (1, 4, 2, 59.98),  -- John Doe purchased 2 Casual Shirts
                                                                                (1, 7, 1, 49.99),  -- John Doe purchased Formal Trousers
                                                                                (1, 10, 1, 99.99), -- John Doe purchased Leather Shoes
                                                                                (2, 5, 1, 59.99),  -- Jane Smith purchased a Summer Dress
                                                                                (2, 14, 2, 99.98), -- Jane Smith purchased 2 Unisex T-shirts
                                                                                (2, 16, 1, 129.99),-- Jane Smith purchased a Winter Jacket
                                                                                (2, 19, 1, 49.99), -- Jane Smith purchased Casual Sneakers
                                                                                (2, 22, 1, 47.99); -- Jane Smith purchased Jeans
