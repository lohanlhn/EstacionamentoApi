-- -----------------------------------------------------
-- Table `Valores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Valores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `minutagem` INT NOT NULL,
  `valor` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `tipo` CHAR(1) NOT NULL,
  `dataAcesso` DATETIME NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `telefone` CHAR(11) NULL DEFAULT NULL,
  `cpf` CHAR(11) NULL DEFAULT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  INDEX `FK_Cliente_Usuario` (`usuario_id` ASC) VISIBLE,
  UNIQUE INDEX `usuario_id_UNIQUE` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK_Cliente_Usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Veiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Veiculo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(100) NULL DEFAULT NULL,
  `cor` VARCHAR(100) NULL DEFAULT NULL,
  `placa` CHAR(7) NOT NULL,
  `tipo` CHAR(1) NOT NULL,
  `cliente_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC) VISIBLE,
  INDEX `FK_Veiculo_Cliente_idx` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Cliente`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Vaga`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Vaga` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `codVaga` CHAR(3) NOT NULL,
  `disponivel` BIT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `codVaga_UNIQUE` (`codVaga` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VagaOcupada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VagaOcupada` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `horaEntrada` DATETIME NOT NULL,
  `horaSaida` DATETIME NULL DEFAULT NULL,
  `valor` DOUBLE NULL DEFAULT NULL,
  `paga` BIT NOT NULL,
  `vaga_id` INT NOT NULL,
  `veiculo_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_Veiculo_Vaga_Vaga_idx` (`vaga_id` ASC) VISIBLE,
  INDEX `FK_Veiculo_Vaga_Veiculo_idx` (`veiculo_id` ASC) VISIBLE,
  CONSTRAINT `FK_Veiculo_Vaga_Vaga`
    FOREIGN KEY (`vaga_id`)
    REFERENCES `Vaga` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Veiculo_Vaga_Veiculo`
    FOREIGN KEY (`veiculo_id`)
    REFERENCES `Veiculo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;