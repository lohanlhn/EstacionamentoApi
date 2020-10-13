CREATE TABLE IF NOT EXISTS `Valores` (
  `idValores` INT NOT NULL,
  `tipo` VARCHAR(100) NOT NULL,
  `valor` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`idValores`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Usuario` (
  `idUsuario` INT NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `tipo` CHAR(1) NOT NULL,
  `dataAcesso` DATETIME NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Cliente` (
  `idCliente` INT NOT NULL,
  `telefone` VARCHAR(12) NULL,
  `cpf` VARCHAR(12) NULL,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  CONSTRAINT `FK_Cliente_Usuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Veiculo` (
  `idVeiculo` INT NOT NULL,
  `marca` VARCHAR(100) NULL,
  `modelo` VARCHAR(100) NULL,
  `cor` VARCHAR(100) NULL,
  `placa` CHAR(7) NOT NULL,
  `tipo` CHAR(1) NULL,
  `idCliente` INT NOT NULL,
  PRIMARY KEY (`idVeiculo`),
  INDEX `FK_Veiculo_Cliente_idx` (`idCliente` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Cliente`
    FOREIGN KEY (`idCliente`)
    REFERENCES `Cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `Vaga` (
  `idVaga` INT NOT NULL,
  `patio` VARCHAR(100) NOT NULL,
  `disponivel` BIT NOT NULL,
  `codVaga` CHAR(3) NOT NULL,
  PRIMARY KEY (`idVaga`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Veiculo_Vaga` (
  `idVeiculo_Vaga` INT NOT NULL,
  `horaEntrada` DATETIME NOT NULL,
  `horaSaida` DATETIME NULL,
  `valor` DECIMAL(5,2) NULL,
  `idVaga` INT NOT NULL,
  `idVeiculo` INT NOT NULL,
  PRIMARY KEY (`idVeiculo_Vaga`),
  INDEX `FK_Veiculo_Vaga_Vaga_idx` (`idVaga` ASC) VISIBLE,
  INDEX `FK_Veiculo_Vaga_Veiculo_idx` (`idVeiculo` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Vaga_Vaga`
    FOREIGN KEY (`idVaga`)
    REFERENCES `Vaga` (`idVaga`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Veiculo_Vaga_Veiculo`
    FOREIGN KEY (`idVeiculo`)
    REFERENCES `Veiculo` (`idVeiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE `Regra` (
  `idRegra` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`idRegra`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;
 
CREATE TABLE `Usuario_Regra` (
  `idUsuario` INT NOT NULL,
  `idRegra` INT NOT NULL,
  PRIMARY KEY (`idUsuario`, `idRegra`),
  INDEX `fk_Usuario_Regra_regraIdx` (`idRegra` ASC),
  INDEX `fk_Usuario_regraIdUsuariox` (`idUsuario` ASC),
  CONSTRAINT `fk_Usuario_Regra_Usuario`
    FOREIGN KEY (`idUsuario`) 
    REFERENCES `Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Regra_Regra`
    FOREIGN KEY (`idRegra`)
    REFERENCES `Regra` (`idRegra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;