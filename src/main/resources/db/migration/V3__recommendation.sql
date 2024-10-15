CREATE TABLE event (
                       event_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_id BIGINT,
                       event_type VARCHAR(100) NOT NULL,
                       style_preferences VARCHAR(255),
                       color_preferences VARCHAR(15),
                       size_preferences VARCHAR(5),
                       event_date DATE,
                       budget DECIMAL(10, 2),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE recommendation (
                                recommendation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                event_id BIGINT NOT NULL,
                                recommended_items TEXT NOT NULL,
                                budget DECIMAL(10, 2),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (event_id) REFERENCES event(event_id)
);


CREATE INDEX idx_user_id ON event(user_id);
CREATE INDEX idx_event_id ON recommendation(event_id);
