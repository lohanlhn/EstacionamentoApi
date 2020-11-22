ALTER TABLE `estacionamento`.`veiculo` 
DROP FOREIGN KEY `FK_Veiculo_Cliente`;

ALTER TABLE `estacionamento`.`veiculo` 
DROP COLUMN `cliente_id`,
ADD COLUMN `usuario_id` INT NOT NULL,
ADD INDEX `FK_Veiculo_Usuario_idx` (`usuario_id` ASC) VISIBLE,
DROP INDEX `FK_Veiculo_Cliente_idx` ;


ALTER TABLE `estacionamento`.`veiculo` 
ADD CONSTRAINT `FK_Veiculo_Usuario`
  FOREIGN KEY (`usuario_id`)
  REFERENCES `estacionamento`.`usuario` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `estacionamento`.`usuario` 
ADD COLUMN `telefone` VARCHAR(11) NOT NULL AFTER `tipo`;
  
  
DROP TABLE `estacionamento`.`cliente`;