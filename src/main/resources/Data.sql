CREATE DATABASE RestaurantBookingDB;
GO

USE RestaurantBookingDB;
GO

-- 1. Bảng Customer (Khách hàng)
CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, Tự tăng (PK)
    FullName NVARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(15) UNIQUE NOT NULL, -- Số điện thoại phải là duy nhất
    Email VARCHAR(100) UNIQUE NULL,
    PasswordHash VARCHAR(255) NOT NULL -- Lưu mật khẩu đã mã hóa
);

-- 2. Bảng Dish (Món ăn)
CREATE TABLE Dish (
    DishID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    Name NVARCHAR(255) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Description NVARCHAR(MAX) NULL,
    ImageURL VARCHAR(500) NULL,
    Category NVARCHAR(50) NULL,
    DishType VARCHAR(20) NULL, -- single, combo, set menu
    Status VARCHAR(20) NOT NULL -- active, inactive, out_of_stock
);

-- 3. Bảng Space (Khu vực)
CREATE TABLE Space (
    SpaceID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    SpaceName NVARCHAR(50) UNIQUE NOT NULL, -- Tên khu vực phải là duy nhất
    Description NVARCHAR(MAX) NULL,
    ImageURL VARCHAR(500) NULL
);

-- 4. Bảng Table (Bàn) - Dùng [Table] vì TABLE là từ khóa của SQL
CREATE TABLE [Table] (
    TableID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    TableName VARCHAR(20) NOT NULL,
    SpaceID INT NOT NULL, -- Khóa ngoại tham chiếu đến Space (FK)
    Seats INT NOT NULL,
    Status VARCHAR(20) NOT NULL, -- available, reserved, occupied, maintenance
    CONSTRAINT FK_Table_Space FOREIGN KEY (SpaceID) REFERENCES Space(SpaceID)
);

-- 5. Bảng Service (Dịch vụ đi kèm)
CREATE TABLE Service (
    ServiceID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    Name NVARCHAR(100) UNIQUE NOT NULL,
    ExtraFee DECIMAL(10, 2) NOT NULL
);

-- 6. Bảng Promotion (Khuyến mãi)
CREATE TABLE Promotion (
    PromoID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    PromoName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX) NULL,
    DiscountPercent DECIMAL(5, 2) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    ImageURL VARCHAR(500) NULL,
    Status VARCHAR(20) NOT NULL -- Active, Expired, Upcoming
);

-- 7. Bảng Booking (Đơn đặt bàn)
CREATE TABLE Booking (
    BookingID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    CustomerID INT NOT NULL, -- Khóa ngoại tham chiếu đến Customer (FK)
    TableID INT NULL, -- Khóa ngoại tham chiếu đến Table (FK, NULL nếu không chỉ định bàn)
    ServiceID INT NULL, -- Khóa ngoại tham chiếu đến Service (FK)
    BookingTime DATETIME NOT NULL,
    NumberOfGuests INT NOT NULL,
    Note NVARCHAR(MAX) NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(20) NOT NULL, -- PENDING, CONFIRMED, CANCELLED, COMPLETED
    BookingType VARCHAR(20) NOT NULL, -- DINE_IN, PARTY
    
    CONSTRAINT FK_Booking_Customer FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    CONSTRAINT FK_Booking_Table FOREIGN KEY (TableID) REFERENCES [Table](TableID),
    CONSTRAINT FK_Booking_Service FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)
);

-- 8. Bảng BookingDetail (Chi tiết Đơn đặt bàn)
CREATE TABLE BookingDetail (
    BookingDetailID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
    BookingID INT NOT NULL, -- Khóa ngoại tham chiếu đến Booking (FK)
    DishID INT NOT NULL, -- Khóa ngoại tham chiếu đến Dish (FK)
    Quantity INT NOT NULL,
    PriceAtOrder DECIMAL(10, 2) NOT NULL, -- Giá món tại thời điểm đặt (quan trọng)
    Total DECIMAL(10, 2) NOT NULL,
    
    CONSTRAINT FK_BookingDetail_Booking FOREIGN KEY (BookingID) REFERENCES Booking(BookingID),
    CONSTRAINT FK_BookingDetail_Dish FOREIGN KEY (DishID) REFERENCES Dish(DishID)
);

-- 9. Bảng TableSchedule (Lịch đặt bàn)
CREATE TABLE TableSchedule (
    ScheduleID INT IDENTITY(1,1) PRIMARY KEY,
    TableID INT NOT NULL,
    BookingID INT NOT NULL,
    ReservationDate DATE NOT NULL,
    StartTime TIME NOT NULL,
    EndTime TIME NOT NULL,
    Status VARCHAR(20) NOT NULL, -- BOOKED, CANCELLED, COMPLETED

    CONSTRAINT FK_Schedule_Table FOREIGN KEY (TableID) REFERENCES [Table](TableID),
    CONSTRAINT FK_Schedule_Booking FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);


-- Dữ liệu

-- 1. Bảng Customer (Khách hàng)
INSERT INTO Customer (FullName, PhoneNumber, Email, PasswordHash)
    VALUES
    (N'Nguyễn Văn A', '0123456789', 'a@gmail.com', '123'),
    (N'Trần Thị B', '0987654321', 'b@gmail.com', '123'),
    (N'Lê Văn C', '0911222333', 'c@gmail.com', '123');

-- 2. Bảng Dish (Món ăn)
    -- COMBO
    INSERT INTO Dish (Name, Price, Description, ImageURL, Category, DishType, Status) VALUES
    (N'Combo FA',899000,N'Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.',
    'https://oms.hotdeal.vn/images/editors/sources/000365904896/365904-phu-nuong-combo-nuong-danh-cho-4-nguoi-body(6).jpg',
    'BBQ','combo','active'),

    (N'Combo Gia Đình',899000,N'Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.',
    'https://hotdeal.vn/images/uploads/2016/Thang%2011/18/306310/306310-combo-lau-nuong-nha-hang-lau-ngon-124-lang-ha-body-1.jpg',
    'BBQ','combo','active'),

    (N'Combo Bạn Bè',499000,N'Phù hợp cho 2-4 người, nhiều món nướng hấp dẫn.',
    'https://static.hotdeal.vn/images/1385/1384835/500x500/311171-set-nuong-lau-an-tet-ga-cho-4-6-nguoi-tai-nha-hang-lau-69.jpg',
    'BBQ','combo','active'),

    (N'Combo Cặp Đôi',299000,N'Thực đơn lãng mạn dành cho 2 người.',
    'https://statics.vincom.com.vn/xu-huong/chi_tiet_xu_huong/buffet-lau-nuong-da-dang-hap-dan-1649053624.jpg',
    'BBQ','combo','active'),

    (N'Pizza Pepperoni',150000,N'Pizza Pepperoni size M',
    'https://file.hstatic.net/1000389344/article/pepperoni_5_1c9ba759196f480eba397d628ac958ed_1024x1024.jpg',
    'Pizza','pizza','active');
    -- THỊT & HẢI SẢN
    INSERT INTO Dish VALUES
    (N'Bò nướng lá lốt',150000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://fujifoods.vn/wp-content/uploads/2021/05/bo-nuong-la-lot-2-1.jpg','meat','single','active'),

    (N'Gà nướng sả',120000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://i.ytimg.com/vi/0Gmszm1DEuk/maxresdefault.jpg','meat','single','active'),

    (N'Sườn nướng BBQ',180000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://static-images.vnncdn.net/files/publish/2022/6/5/mon-ngon-238.jpg','meat','single','active'),

    (N'Tôm nướng muối ớt',220000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://media.loveitopcdn.com/5827/kcfinder/upload/images/t%C3%B4m%20s%C3%BA%20n%C6%B0%E1%BB%9Bng%20sat%E1%BA%BF.jpg','meat','single','active'),

    (N'Bò cuộn nấm kim châm',165000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://thucpham3a.com/wp-content/uploads/2023/08/cach-lam-thit-bo-ba-chi-cuon-nam-kim-cham-1.jpeg','meat','single','active'),

    (N'Bạch tuộc nướng sa tế',195000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz4X_JGqjI8Kufqfoo7jxXKVP9_vcv6cNYqA&s','meat','single','active'),

    (N'Mực một nắng nướng',280000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://quadanang.com/wp-content/uploads/2022/05/muc-mot-nang-gia-re-Da-Nang.jpg','meat','single','active'),

    (N'Bò Fillet sốt tiêu đen',260000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://barona.vn/storage/meo-vat/53/bo-sot-tieu-den.jpg','meat','single','active');
    --RAU CỦ
    INSERT INTO Dish VALUES
    (N'Bắp nướng bơ tỏi',50000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://www.cgvdt.vn/files/ckfinder/images/2017/G%C4%90/2122/Hinh%20Bap%20nuong%20bo%20toi%20-%20Goc%20bep.jpg','veg','single','active'),

    (N'Nấm chiên lắc phô mai',80000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://cdn.tgdd.vn/2021/10/CookRecipe/GalleryStep/thanh-pham-1189.jpg','veg','single','active'),

    (N'Cà tím nướng mỡ hành',65000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://file.hstatic.net/200000700229/article/lam-ca-tim-nuong-mo-hanh-bang-noi-chien-khong-dau_95456b83f1fa4910a60342e76347b907.jpg','veg','single','active'),

    (N'Salad trộn dầu giấm',75000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://beptruong.edu.vn/wp-content/uploads/2016/01/mon-salad-dau-giam.jpg','veg','single','active');
    -- LẨU
    INSERT INTO Dish VALUES
    (N'Lẩu hải sản chua cay',250000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://beptruong.edu.vn/wp-content/uploads/2021/03/lau-hai-san-chua-cay-ngon.jpg','soup','hotpot','active'),

    (N'Lẩu bò khoai môn',280000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://cdn.tgdd.vn/2021/09/CookProduct/laubokhoaimon-1200x676.jpg','soup','hotpot','active'),

    (N'Lẩu gà lá é',240000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2023_10_10_638325588933867737_lau-ga-la-e-0.jpeg','soup','hotpot','active'),

    (N'Lẩu kim chi Hàn Quốc',230000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://cdn.netspace.edu.vn/images/2018/10/25/cach-nau-lau-kim-chi-chuan-vi-han-quoc-800.jpg','soup','hotpot','active');
    -- THỨC UỐNG
    INSERT INTO Dish VALUES
    (N'Coca-Cola (Lon)',20000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://pvmarthanoi.com.vn/wp-content/uploads/2023/02/nuoc-ngot-coca-cola-330ml-202001131632421470-500x600.png','drink','single','active'),

    (N'Trà Đào Cam Sả',45000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://thecupcafevietnam.com/wp-content/uploads/2022/10/tra-dao-cam-sa-1.png','drink','single','active'),

    (N'Nước ép Cam tươi',50000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://nhahangchayhuongsen.com/image/cache/catalog/san-pham/giai-khat/hs4-800x800.png','drink','single','active'),

    (N'Bia Heineken',35000,N'Món ăn hấp dẫn, chuẩn vị nhà hàng.',
    'https://www.vitaminhouse.vn/cdn/shop/files/chenlogovitaminhouse-2024-06-25T115358.136_600x600_crop_center.png','drink','single','active');

-- 3. Bảng Promotion (Khuyến mãi)
INSERT INTO Promotion
(PromoName, Description, DiscountPercent, StartDate, EndDate, ImageURL, Status)
VALUES
(N'Happy Hour',
 N'Giảm 20% từ 14h–17h mỗi ngày',
 20.0,
 '2025-11-01',
 '2025-11-30',
 'https://images.unsplash.com/photo-1551218808-94e220e084d2',
 'Active'),

(N'Combo Cặp Đôi',
 N'Không gian lãng mạn + menu đặc biệt cho 2 người',
 30.0,
 '2025-11-01',
 '2025-12-15',
 'https://images.unsplash.com/photo-1551218808-94e220e084d2',
 'Active'),

(N'Combo Gia Đình',
 N'Giảm 15% cuối tuần',
 15.0,
 '2025-11-01',
 '2025-12-31',
 'https://images.unsplash.com/photo-1551218808-94e220e084d2',
 'Inactive');

-- 4. Bảng Space (Khu vực)
 INSERT INTO Space (SpaceName, Description, ImageURL)
VALUES
(N'Tầng 1 - Khu thường',
 N'Không gian thoải mái cho gia đình',
 'https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg'),

(N'Tầng 2 - Khu VIP',
 N'Không gian riêng tư, sang trọng',
 'https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg'),

(N'Sân vườn ngoài trời',
 N'Thưởng thức BBQ ngoài trời',
 'https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg');

 -- 5. Bảng Table (Bàn) - Dùng [Table] vì TABLE là từ khóa của SQL
    -- Lầu 1
    INSERT INTO [Table] (SpaceID, Seats, Status, TableName) VALUES
    (1, 4, 'available', 'A01'),
    (1, 4, 'available', 'A02'),
    (1, 4, 'available', 'A03'),
    (1, 4, 'available', 'A04'),
    (1, 4, 'available', 'A05'),
    (1, 4, 'available', 'A06'),
    (1, 4, 'available', 'A07'),
    (1, 4, 'available', 'A08'),
    (1, 4, 'available', 'A09'),
    (1, 4, 'available', 'A10');

    -- VIP
    INSERT INTO [Table] (SpaceID, Seats, Status, TableName) VALUES
    (2, 4, 'available', 'VIP01'),
    (2, 4, 'available', 'VIP02'),
    (2, 4, 'available', 'VIP03'),
    (2, 4, 'available', 'VIP04'),
    (2, 4, 'available', 'VIP05');

    -- Sân vườn
    INSERT INTO [Table] (SpaceID, Seats, Status, TableName) VALUES
    (3, 4, 'available', 'SV01'),
    (3, 4, 'available', 'SV02'),
    (3, 4, 'available', 'SV03'),
    (3, 4, 'available', 'SV04'),
    (3, 4, 'available', 'SV05'),
    (3, 4, 'available', 'SV06');

-- 6.  Bảng Booking
INSERT INTO Booking
(CustomerID, TableID, ServiceID, BookingTime, NumberOfGuests, Note, TotalAmount, Status, BookingType)
VALUES
(1, 1, NULL, '2025-12-20 18:00', 4, N'Khách đặt trước', 0, 'CONFIRMED', 'DINE_IN'), -- BookingID = 1
(2, 2, NULL, '2025-12-20 18:00', 4, N'Khách đặt trước', 0, 'CONFIRMED', 'DINE_IN'), -- BookingID = 2
(3, 5, NULL, '2025-12-20 19:00', 4, N'Khách đặt trước', 0, 'CONFIRMED', 'DINE_IN'), -- BookingID = 3
(1, 12, NULL, '2025-12-20 17:30', 4, N'Khách đặt trước', 0, 'CONFIRMED', 'DINE_IN'); -- BookingID = 4

-- 7. Bảng TableSchedule (Lịch đặt bàn)
INSERT INTO TableSchedule (TableID, BookingID, ReservationDate, StartTime, EndTime, Status)
VALUES
(1, 1, '2025-12-20', '18:00', '20:00', 'BOOKED');

INSERT INTO TableSchedule (TableID, BookingID, ReservationDate, StartTime, EndTime, Status)
VALUES
(2, 2, '2025-12-20', '18:00', '20:00', 'BOOKED'),
(5, 3, '2025-12-20', '19:00', '21:00', 'BOOKED'),
(12, 4, '2025-12-20', '17:30', '19:30', 'BOOKED');

-- 8.  Bảng Service (Dịch vụ đi kèm)
INSERT INTO Service (Name, ExtraFee)
VALUES
(N'Tự nướng', 0),
(N'Nhân viên nướng dùm', 150000),
(N'Trang trí sinh nhật', 300000),
(N'Phòng VIP', 200000);
