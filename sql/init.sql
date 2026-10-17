CREATE DATABASE IF NOT EXISTS wechat_clothing CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE wechat_clothing;

CREATE TABLE IF NOT EXISTS products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  image_url VARCHAR(512),
  inventory INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO products (name, description, price, image_url, inventory) VALUES
('棉质短袖 T 恤', '舒适透气的棉质短袖', 59.00, 'https://via.placeholder.com/300x300?text=T%E6%81%A4', 100),
('牛仔裤', '经典直筒牛仔裤', 199.00, 'https://via.placeholder.com/300x300?text=%E7%89%9B%E4%BB%94%E8%A3%A4', 50),
('连衣裙', '优雅连衣裙，适合日常与通勤', 249.00, 'https://via.placeholder.com/300x300?text=%E8%BF%9E%E8%A1%A3%E8%A3%99', 30);
