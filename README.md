# 🏡 Proyecto de Reservas Inmobiliarias con Spring Boot

Este proyecto es una aplicación web desarrollada con **Spring Boot** que permite a los usuarios realizar reservas de inmuebles. La plataforma incluye autenticación segura, pasarela de pagos con Stripe, verificación reCAPTCHA, carga de imágenes en la nube, y una arquitectura limpia basada en MVC.

---

## 🚀 Tecnologías Utilizadas

- **Spring Boot 3+**
- **Spring MVC**
- **Spring Security**
- **Thymeleaf**
- **MySQL + JPA (Hibernate)**
- **Stripe Checkout API**
- **Google reCAPTCHA v2**
- **Cloudinary (almacenamiento de imágenes)**
- **Google Cloud Platform**
- **AWS RDS (base de datos en la nube)**
- **Bootstrap 5 + SweetAlert2**
- **Session Management**

---

## ⚙️ Funcionalidades

### 🔒 Seguridad y Control de Acceso
- Autenticación para clientes y administradores
- Roles separados con acceso personalizado
- Validación de usuarios sancionados o activos
- Protección con Google reCAPTCHA v2 en login

### 🗓️ Reservas
- Selección de fechas y validación de disponibilidad
- Cálculo automático del monto total
- Pasarela de pago Stripe integrada
- Registro automático de reservas y pagos tras confirmación exitosa
- Generación de PDF con los detalles de la reserva

### ☁️ Servicios Cloud
- Carga y gestión de imágenes con **Cloudinary**
- Uso de **AWS RDS** para base de datos MySQL escalable
- Compatible con despliegue en **Google Cloud** o **Firebase Hosting**

### 📦 Despliegue
- Proyecto desplegado en **Railway.app**
- Configuración lista para producción y variables externas

---

## 📁 Estructura del Proyecto
<img width="1024" height="1024" alt="a43d735e-47d4-4b3c-9c05-455ff18ce93d" src="https://github.com/user-attachments/assets/9893dd19-26cc-4b90-ab41-9aeb3d179007" />
