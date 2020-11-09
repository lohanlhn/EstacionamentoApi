INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `ativo`, `tipo`, `dataacesso`) VALUES
(DEFAULT, 'Usuario adm', 'adm@email.com', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', TRUE, 'A', CURDATE());
 
INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `ativo`, `tipo`, `dataacesso`) VALUES
(DEFAULT, 'Usuario cliente', 'cliente@email.com', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', TRUE, 'C', CURDATE());

INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `ativo`, `tipo`, `dataacesso`) VALUES
(DEFAULT, 'Usuario funcionario', 'func@email.com', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', TRUE, 'F', CURDATE());
 
INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_ADM', 'Permite acesso aos serviços de usuário', TRUE);
 
INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_FUNC', 'Permite exclusão de cartões', TRUE);
 
INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE email = 'adm@email.com'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_ADM')
);
 
INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE email = 'adm@email.com'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_FUNC')
);

INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE email = 'func@email.com'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_FUNC')
);
