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
    Name NVARCHAR(50) UNIQUE NOT NULL, -- Tên khu vực phải là duy nhất
    Description NVARCHAR(MAX) NULL,
    ImageURL VARCHAR(500) NULL
);

-- 4. Bảng Table (Bàn) - Dùng [Table] vì TABLE là từ khóa của SQL
CREATE TABLE [Table] (
    TableID INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính (PK)
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