-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 05, 2021 lúc 08:44 AM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `game_trac_nghiem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question`
--

CREATE TABLE `question` (
  `questionID` int(11) NOT NULL,
  `question` varchar(300) CHARACTER SET utf8 NOT NULL,
  `answerA` varchar(100) CHARACTER SET utf8 NOT NULL,
  `answerB` varchar(100) CHARACTER SET utf8 NOT NULL,
  `answerC` varchar(100) CHARACTER SET utf8 NOT NULL,
  `answerD` varchar(100) CHARACTER SET utf8 NOT NULL,
  `correctAnswer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `question`
--

INSERT INTO `question` (`questionID`, `question`, `answerA`, `answerB`, `answerC`, `answerD`, `correctAnswer`) VALUES
(1, 'HTML là viết tắt của gì ?', 'Hyperlinks and Text Markup Language', 'Hyper Text Markup Language', 'Home Tool Markup Language', 'Home Text Markup Language', 2),
(2, 'Biểu tượng của Java là gì ?', 'Cốc sinh tố', 'Cốc bia', 'Cốc Cafe', 'Cốc Trà', 3),
(3, 'Công ty nào tạo ra chuẩn WEB ?', 'The Word Wide Web Consortium', 'Mozilla', 'Microsof', 'Google', 1),
(4, 'Thẻ nào trong HTML cho heading lớn nhất ?', '<head>', '<h6>', '<heading>', '<h1>', 4),
(5, 'Thẻ nào trong HTML định nghĩa 1 đoạn text quan trọng ?', '<b>', '<i>', '<strong>', '<important>', 4),
(6, 'Cú pháp đúng in ra ouput \"Hello Word\" trong C++ ?', 'cout<< \"Hello World\"', 'cout<< \"Hello World\";', 'cout >> \"Hello World\" ', 'cout>> \"Hello World\";', 2),
(7, 'Trong C++, kiểu dữ liệu nào dùng để tạo ra biến lưu trữ text ?', 'string', 'String', 'txt', 'Txt', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `account` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `point` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`userID`, `account`, `password`, `status`, `point`) VALUES
(1, 'tamnm', '123', 1, 10),
(2, 'bachnn', '123456', 1, 7.5),
(3, 'quangnt', 'abc', 0, 4),
(4, 'hungdm', 'abcde', 0, 1),
(5, 'khanhbd', '987', 0, 2),
(6, 'khoihd', '98765', 0, 1),
(7, 'lynth', '123', 0, 4.5);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`questionID`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `question`
--
ALTER TABLE `question`
  MODIFY `questionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
