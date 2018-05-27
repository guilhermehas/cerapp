-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tempo de Geração: 02/06/2016 às 17:48
-- Versão do servidor: 5.5.44-0ubuntu0.14.04.1
-- Versão do PHP: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Banco de dados: `cerveja`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cerveja`
--

CREATE TABLE IF NOT EXISTS `cerveja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `fabricante` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `porcentagem` float DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Fazendo dump de dados para tabela `cerveja`
--

INSERT INTO `cerveja` (`id`, `nome`, `fabricante`, `tipo`, `porcentagem`, `pais`) VALUES
(1, 'Antarctica Original', 'Ambev', NULL, NULL, NULL),
(2, 'Bohemia Weiss', 'Ambev', NULL, NULL, NULL),
(3, 'Brahma Extra', 'Ambev', NULL, NULL, NULL),
(4, 'Skol', 'Ambev', NULL, NULL, NULL),
(5, 'Budweiser', 'Ambev', NULL, NULL, NULL),
(6, 'Eisenbahn Dunkel', 'Sudbrack', NULL, NULL, NULL),
(7, 'Colonia Pilsen', 'Colonia', NULL, NULL, NULL),
(8, 'Nova Schin Pilsen', 'Schincariol', NULL, NULL, NULL),
(9, 'Primus', 'Schincariol', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `cervejaEstabelecimento`
--

CREATE TABLE IF NOT EXISTS `cervejaEstabelecimento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estabelecimento_id` int(11) NOT NULL,
  `cerveja_id` int(11) NOT NULL,
  `preco` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unic` (`estabelecimento_id`,`cerveja_id`),
  KEY `cervejaestabelecimento_nome_fkey` (`cerveja_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Fazendo dump de dados para tabela `cervejaEstabelecimento`
--

INSERT INTO `cervejaEstabelecimento` (`id`, `estabelecimento_id`, `cerveja_id`, `preco`) VALUES
(2, 1, 1, 1),
(5, 1, 8, 1.11),
(6, 1, 4, 1.05),
(7, 1, 3, 1.22),
(8, 1, 2, 1.1),
(9, 2, 1, 1.2),
(10, 2, 4, 1.4),
(11, 2, 6, 1.3),
(12, 3, 4, 2),
(13, 3, 2, 1.3),
(14, 3, 7, 3),
(15, 4, 3, 1.06);

-- --------------------------------------------------------

--
-- Estrutura para tabela `denuncia`
--

CREATE TABLE IF NOT EXISTS `denuncia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estabelecimento_id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `denuncia_cnpj_fkey` (`estabelecimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura para tabela `estabelecimento`
--

CREATE TABLE IF NOT EXISTS `estabelecimento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `nomeOficial` varchar(255) DEFAULT NULL,
  `nomeComercial` varchar(255) DEFAULT NULL,
  `CNPJ` char(255) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `CNPJ` (`CNPJ`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Fazendo dump de dados para tabela `estabelecimento`
--

INSERT INTO `estabelecimento` (`id`, `email`, `senha`, `nomeOficial`, `nomeComercial`, `CNPJ`, `latitude`, `longitude`) VALUES
(1, 'bbzinhodomal@hotmail.com', '12345', 'Mureta', 'Silva', '22222222222222', -22.9507, -43.1678),
(2, 'gui@hotmail.com', '12345', 'Shooters', 'Shooters', '23123222222222', -22.9483, -43.1854),
(3, 'sig_gvt@hotmail.com', '12345', 'Skol 360', 'Skol 360', '87921372983232', -22.9737, -43.2272),
(4, 'aaa@hotmail.com', '12345', 'Insttituto Militar', 'Instituto Militar', '23123123213133', -22.9557, -43.1665);

-- --------------------------------------------------------

--
-- Estrutura para tabela `itemDiversoEstabelecimento`
--

CREATE TABLE IF NOT EXISTS `itemDiversoEstabelecimento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `preco` int(11) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `estabelecimento_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`),
  KEY `itemdiversoestabelecimento_cnpj_fkey` (`estabelecimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Restrições para dumps de tabelas
--

--
-- Restrições para tabelas `cervejaEstabelecimento`
--
ALTER TABLE `cervejaEstabelecimento`
  ADD CONSTRAINT `cervejaestabelecimento_cnpj_fkey` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`),
  ADD CONSTRAINT `cervejaestabelecimento_nome_fkey` FOREIGN KEY (`cerveja_id`) REFERENCES `cerveja` (`id`);

--
-- Restrições para tabelas `denuncia`
--
ALTER TABLE `denuncia`
  ADD CONSTRAINT `denuncia_cnpj_fkey` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`);

--
-- Restrições para tabelas `itemDiversoEstabelecimento`
--
ALTER TABLE `itemDiversoEstabelecimento`
  ADD CONSTRAINT `itemdiversoestabelecimento_cnpj_fkey` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
