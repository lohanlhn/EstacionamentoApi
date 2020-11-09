CREATE TABLE `Regra` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `ativo` BIT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;
 
CREATE TABLE `Usuario_Regra` (
  `usuario_id` INT NOT NULL,
  `regra_id` INT NOT NULL,
  PRIMARY KEY (`usuario_id`, `regra_id`),
  INDEX `fk_Usuario_Regra_Regra_idx` (`regra_id` ASC),
  INDEX `fk_Usuario_Regra_Usuario_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_Usuario_Regra_Usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Regra_Regra`
    FOREIGN KEY (`regra_id`)
    REFERENCES `Regra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
