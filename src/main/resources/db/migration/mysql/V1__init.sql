CREATE TABLE IF NOT EXISTS `Valores` (
  `id` INT NOT NULL,
  `minutagem` VARCHAR(100) NOT NULL,
  `valor` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` INT NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `tipo` CHAR(1) NOT NULL,
  `dataAcesso` DATETIME NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cliente` (
  `id` INT NOT NULL,
  `telefone` CHAR(11) NULL,
  `cpf` VARCHAR(12) NULL,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  CONSTRAINT `FK_Cliente_Usuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Veiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Veiculo` (
  `id` INT NOT NULL,
  `marca` VARCHAR(100) NULL,
  `cor` VARCHAR(100) NULL,
  `placa` CHAR(7) NOT NULL,
  `tipo` CHAR(1) NULL,
  `idCliente` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC) VISIBLE,
  INDEX `FK_Veiculo_Cliente_idx` (`idCliente` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Cliente`
    FOREIGN KEY (`idCliente`)
    REFERENCES `Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Vaga`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Vaga` (
  `id` INT NOT NULL,
  `codVaga` CHAR(3) NOT NULL,
  `disponivel` BIT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Veiculo_Vaga`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Vaga_Ocupada` (
  `id` INT NOT NULL,
  `horaEntrada` DATETIME NOT NULL,
  `horaSaida` DATETIME NULL,
  `valor` DOUBLE NULL,
  `idVaga` INT NOT NULL,
  `idVeiculo` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_Veiculo_Vaga_Vaga_idx` (`idVaga` ASC) VISIBLE,
  INDEX `FK_Veiculo_Vaga_Veiculo_idx` (`idVeiculo` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Vaga_Vaga`
    FOREIGN KEY (`idVaga`)
    REFERENCES `Vaga` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Veiculo_Vaga_Veiculo`
    FOREIGN KEY (`idVeiculo`)
    REFERENCES `Veiculo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
