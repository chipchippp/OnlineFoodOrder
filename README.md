# OnlineFoodOrder
﻿# OnlineFoodOrder
---
### **Bài toán bạn đang giải quyết**
1. **Quản lý người dùng**:
- Người dùng có thể đăng ký, đăng nhập, và quản lý thông tin cá nhân.
- Phân quyền người dùng (Admin, Chủ nhà hàng, Khách hàng) để đảm bảo các chức năng được truy cập đúng vai trò.


2. **Quản lý nhà hàng**:
- Admin hoặc chủ nhà hàng có thể thêm, sửa, xóa thông tin nhà hàng.
- Mỗi nhà hàng có thông tin chi tiết như địa chỉ, thông tin liên hệ, danh sách món ăn, và trạng thái hoạt động.


3. **Quản lý món ăn**:
- Chủ nhà hàng có thể thêm, sửa, xóa món ăn trong thực đơn.
- Mỗi món ăn có thông tin như tên, giá, mô tả, hình ảnh, nguyên liệu, và trạng thái (còn hàng hoặc hết hàng).


4. **Quản lý đơn hàng**:
- Khách hàng có thể đặt món ăn từ nhà hàng.
- Đơn hàng bao gồm thông tin khách hàng, nhà hàng, danh sách món ăn, địa chỉ giao hàng, và trạng thái đơn hàng.


5. **Xác thực và bảo mật**:
- Sử dụng **JWT** để xác thực người dùng và bảo vệ các API.
- Chỉ những người dùng có vai trò phù hợp mới được phép thực hiện các hành động cụ thể.

---

### **Tư tưởng thiết kế**
1. **Tách biệt trách nhiệm (Separation of Concerns)**:
- Bạn đã chia ứng dụng thành các lớp và module riêng biệt như `Controller`, `Service`, `Repository`, và `Model`. Điều này giúp mã nguồn dễ bảo trì và mở rộng.
- Ví dụ:
    - `FoodController` xử lý các yêu cầu liên quan đến món ăn.
    - `RestaurantController` xử lý các yêu cầu liên quan đến nhà hàng.


2. **Sử dụng các thực thể (Entities) để ánh xạ cơ sở dữ liệu**:
- Các lớp như `UserEntity`, `Restaurant`, `Food`, `Order` được ánh xạ trực tiếp với các bảng trong cơ sở dữ liệu thông qua JPA/Hibernate.
- Điều này giúp bạn dễ dàng thao tác với dữ liệu mà không cần viết nhiều câu lệnh SQL thủ công.


3. **Quản lý quan hệ giữa các thực thể**:
- Bạn đã thiết kế các mối quan hệ giữa các thực thể như `OneToMany`, `ManyToOne`, `ManyToMany` để phản ánh đúng logic nghiệp vụ.
- Ví dụ:
    - Một nhà hàng (`Restaurant`) có nhiều món ăn (`Food`).
    - Một đơn hàng (`Order`) có nhiều món ăn (`OrderItem`).


4. **Sử dụng DTO (Data Transfer Object)**:
- Bạn sử dụng các lớp DTO như `FoodRequest`, `RestaurantRequest` để nhận dữ liệu từ client. Điều này giúp tách biệt dữ liệu đầu vào với các thực thể trong cơ sở dữ liệu, đảm bảo tính bảo mật và dễ kiểm soát.


5. **Xử lý vòng đời của bean và tránh circular dependency**:
- Bạn đã gặp vấn đề về circular dependency giữa `AppConfig` và `UserServiceImpl`. Để giải quyết, bạn sử dụng setter injection thay vì constructor injection. Điều này giúp Spring Boot khởi tạo các bean theo đúng thứ tự và tránh lỗi.


6. **Bảo mật và phân quyền**:
- Bạn sử dụng Spring Security để bảo vệ các API. Các endpoint được phân quyền dựa trên vai trò của người dùng.
- Ví dụ:
    - Chỉ `ADMIN` và `RESTAURANT_OWNER` mới có thể truy cập các API quản lý nhà hàng.
    - Các API khác yêu cầu người dùng phải được xác thực.


7. **Sử dụng JWT để xác thực**:
- Bạn sử dụng JWT để xác thực người dùng. Điều này giúp loại bỏ việc sử dụng session và phù hợp với các ứng dụng RESTful.


8. **Tính mở rộng và tái sử dụng**:
- Bạn thiết kế các service như `UserService`, `RestaurantService`, `FoodService` để xử lý logic nghiệp vụ. Điều này giúp mã nguồn dễ mở rộng và tái sử dụng.

---
