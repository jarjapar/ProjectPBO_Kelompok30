-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Des 2024 pada 16.02
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `boladb`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `history`
--

CREATE TABLE `history` (
  `id_histori` int(11) NOT NULL,
  `namaplayer1` varchar(100) DEFAULT NULL,
  `namaplayer2` varchar(100) DEFAULT NULL,
  `skorplayer1` int(11) DEFAULT NULL,
  `skorplayer2` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `history`
--

INSERT INTO `history` (`id_histori`, `namaplayer1`, `namaplayer2`, `skorplayer1`, `skorplayer2`) VALUES
(36, 'aldik', 'poke', 12, 1),
(37, 'aldik', 'poke', 13, 1),
(38, 'rayes', 'dipo', 2, 0),
(39, 'ada', 'wong', 0, 1),
(40, 'ada', 'wong', 0, 2),
(41, 'ada', 'wong', 0, 3),
(42, 'ada', 'wong', 0, 4),
(43, 'ada', 'wong', 0, 5),
(44, 'ada', 'wong', 0, 6),
(45, 'ada', 'wong', 0, 7),
(46, 'ada', 'wong', 0, 8),
(47, 'ada', 'wong', 0, 9),
(48, 'ada', 'wong', 0, 10),
(49, 'ada', 'wong', 0, 11),
(50, 'ada', 'wong', 0, 12),
(51, 'ada', 'wong', 0, 13),
(52, 'ada', 'wong', 0, 14),
(53, 'ada', 'wong', 0, 15),
(54, 'ada', 'wong', 0, 16),
(55, 'ada', 'wong', 0, 17),
(56, 'ada', 'wong', 0, 18),
(57, 'ada', 'wong', 0, 19),
(58, 'ada', 'wong', 0, 20),
(59, 'ada', 'wong', 0, 21),
(60, 'ada', 'wong', 0, 22),
(61, 'ada', 'wong', 0, 23),
(62, 'ada', 'wong', 0, 24),
(63, 'ada', 'wong', 0, 25),
(64, 'ada', 'wong', 0, 26),
(65, 'ada', 'wong', 0, 27),
(66, 'ada', 'wong', 0, 28),
(67, 'ada', 'wong', 0, 29),
(68, 'ada', 'wong', 0, 30),
(69, 'ada', 'wong', 0, 31),
(70, 'ada', 'wong', 0, 32),
(71, 'ada', 'wong', 0, 33),
(72, 'ada', 'wong', 0, 34),
(73, 'ada', 'wong', 0, 35),
(74, 'ada', 'wong', 0, 36),
(75, 'ada', 'wong', 0, 37),
(76, 'ada', 'wong', 0, 38),
(77, 'ada', 'wong', 0, 39),
(78, 'ada', 'wong', 0, 40),
(79, 'ada', 'wong', 0, 41),
(80, 'ada', 'wong', 0, 42),
(81, 'ada', 'wong', 0, 43),
(82, 'ada', 'wong', 0, 44),
(83, 'ada', 'wong', 0, 45),
(84, 'ada', 'wong', 0, 46),
(85, 'ada', 'wong', 0, 46),
(86, 'ada', 'wong', 0, 47),
(87, 'ada', 'wong', 0, 48),
(88, 'ada', 'wong', 0, 49),
(89, 'ada', 'wong', 0, 50),
(90, 'ada', 'wong', 0, 51),
(91, 'ada', 'wong', 0, 52),
(92, 'ada', 'wong', 0, 53),
(93, 'ada', 'wong', 0, 54),
(94, 'ada', 'wong', 0, 55),
(95, 'ada', 'wong', 0, 56),
(96, 'ada', 'wong', 0, 57),
(97, 'ada', 'wong', 0, 58),
(98, 'ada', 'wong', 0, 59),
(99, 'ada', 'wong', 0, 60),
(100, 'ada', 'wong', 0, 61),
(101, 'ada', 'wong', 0, 62),
(102, 'ada', 'wong', 0, 63),
(103, 'ada', 'wong', 0, 64),
(104, 'ada', 'wong', 0, 65),
(105, 'ada', 'wong', 0, 66),
(106, 'ada', 'wong', 0, 67),
(107, 'ada', 'wong', 0, 68),
(108, 'ada', 'wong', 0, 69),
(109, 'ada', 'wong', 0, 70),
(110, 'ada', 'wong', 0, 71),
(111, 'ada', 'wong', 0, 72),
(112, 'ada', 'wong', 0, 73),
(113, 'ada', 'wong', 0, 74),
(114, 'ada', 'wong', 0, 75),
(115, 'ada', 'wong', 0, 76),
(116, 'ada', 'wong', 0, 77),
(117, 'ada', 'wong', 0, 78),
(118, 'ada', 'wong', 0, 79),
(119, 'ada', 'wong', 0, 80),
(120, 'ada', 'wong', 0, 81),
(121, 'ada', 'wong', 0, 82),
(122, 'ada', 'wong', 0, 83),
(123, 'ada', 'wong', 0, 84),
(124, 'ada', 'wong', 0, 85),
(125, 'ada', 'wong', 0, 86),
(126, 'ada', 'wong', 0, 87),
(127, 'ada', 'wong', 0, 88),
(128, 'ada', 'wong', 0, 89),
(129, 'ada', 'wong', 0, 90),
(130, 'ada', 'wong', 0, 91),
(131, 'ada', 'wong', 0, 92),
(132, 'ada', 'wong', 0, 93),
(133, 'ada', 'wong', 0, 94),
(134, 'ada', 'wong', 0, 95),
(135, 'ada', 'wong', 0, 96),
(136, 'ada', 'wong', 0, 97),
(137, 'ada', 'wong', 0, 98),
(138, 'ada', 'wong', 0, 99),
(139, 'ada', 'wong', 0, 100),
(140, 'ada', 'wong', 0, 101),
(141, 'ada', 'wong', 0, 102),
(142, 'ada', 'wong', 0, 103),
(143, 'ada', 'wong', 0, 104),
(144, 'ada', 'wong', 0, 105),
(145, 'ada', 'wong', 0, 106),
(146, 'ada', 'wong', 0, 107),
(147, 'ada', 'wong', 0, 108),
(148, 'ada', 'wong', 0, 109),
(149, 'ada', 'wong', 0, 110),
(150, 'ada', 'wong', 0, 111),
(151, 'ada', 'wong', 0, 112),
(152, 'ada', 'wong', 0, 113),
(153, 'ada', 'wong', 0, 114),
(154, 'ada', 'wong', 0, 115),
(155, 'ada', 'wong', 0, 116),
(156, 'ada', 'wong', 0, 117),
(157, 'ada', 'wong', 0, 118),
(158, 'ada', 'wong', 0, 119),
(159, 'ada', 'wong', 0, 120),
(160, 'ada', 'wong', 0, 121),
(161, 'ada', 'wong', 0, 122),
(162, 'ada', 'wong', 0, 123),
(163, 'ada', 'wong', 0, 124),
(164, 'ada', 'wong', 0, 125),
(165, 'ada', 'wong', 0, 126),
(166, 'ada', 'wong', 0, 127),
(167, 'ada', 'wong', 0, 128),
(168, 'ada', 'wong', 0, 129),
(169, 'ada', 'wong', 0, 130),
(170, 'ada', 'wong', 0, 131),
(171, 'ada', 'wong', 0, 132),
(172, 'ada', 'wong', 0, 133),
(173, 'ada', 'wong', 0, 134),
(174, 'addad', 'adadsa', 4, 2),
(175, 'ADSAS', 'ASDASD', 1, 0),
(176, 'ADSAS', 'ASDASD', 2, 0),
(177, 'ADSAS', 'ASDASD', 3, 0),
(178, 'ADSAS', 'ASDASD', 4, 0),
(179, 'ADSAS', 'ASDASD', 5, 0),
(180, 'ADSAS', 'ASDASD', 6, 0),
(181, 'ADSAS', 'ASDASD', 7, 0),
(182, 'ADSAS', 'ASDASD', 8, 0),
(183, 'ADSAS', 'ASDASD', 9, 0),
(184, 'ADSAS', 'ASDASD', 10, 0),
(185, 'ADSAS', 'ASDASD', 11, 0),
(186, 'ADSAS', 'ASDASD', 12, 0),
(187, 'ADSAS', 'ASDASD', 12, 0),
(188, 'fajar', 'aldau', 0, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `players`
--

INSERT INTO `players` (`id`, `name`, `score`) VALUES
(53, 'JAPAR', 0),
(54, 'ADIT', 3),
(63, '2222', 2),
(64, '3333', 0),
(65, 'manusia', 0),
(66, 'ikan', 2),
(67, 'xixxixixi', 1),
(68, 'wkwkwkw', 0),
(69, 'kakak', 0),
(70, 'adek', 1),
(71, 'keren', 0),
(72, 'gacor', 1),
(73, 'kkk', 2),
(74, 'xixixi', 3),
(75, 'dasdad', 0),
(76, 'dadas', 0),
(77, 'asda', 4),
(78, 'ala', 2),
(79, 'fajargege', 2),
(80, 'riplki gege', 0),
(81, 'dasda', 0),
(82, 'dadasdada', 2),
(83, 'p', 0),
(84, 'e', 0),
(85, 'dino', 4),
(86, 'anwar', 9),
(87, 'pajarrrr', 5),
(88, 'kuwiwiw', 10),
(89, 'fajarlolo', 1),
(90, 'aasdasda', 2),
(91, 'dayu', 1),
(92, 'adaw', 3),
(93, 'adaaa', 0),
(94, 'daa', 1),
(95, 'aldik', 13),
(96, 'poke', 1),
(97, 'rayes', 2),
(98, 'dipo', 0),
(99, 'ada', 0),
(100, 'wong', 134),
(101, 'addad', 4),
(102, 'adadsa', 2),
(103, 'ADSAS', 12),
(104, 'ASDASD', 0),
(105, 'fajar', 0),
(106, 'aldau', 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id_histori`);

--
-- Indeks untuk tabel `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `history`
--
ALTER TABLE `history`
  MODIFY `id_histori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=189;

--
-- AUTO_INCREMENT untuk tabel `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
