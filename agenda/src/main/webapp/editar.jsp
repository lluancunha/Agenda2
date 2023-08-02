<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de contatos </title>
<link rel="icon" href="imagens/favicon_fone.png">
<link rel="stylesheet" href="style.css"> 
</head>
<body>

<section class="agenda">
	<h1> Editar Contato </h1>
	<form name="frmContato" action="update">
	<table id="tabela">
		<tr>
			<td>
			<input type="text" class="caixa2" name="idcon" readonly value="<%out.print(request.getAttribute("idcon")); %>">
			</td>
		</tr>
		
		<tr>
			<td>
			<input type="text" class="caixa1" name="nome" value="<%out.print(request.getAttribute("nome")); %>">
			</td>
			</tr>
			
			<tr>
			<td>
			<input type="text" class="caixa2" name="fone" value="<%out.print(request.getAttribute("fone")); %>">
			</td>
		</tr>
			
			<tr>
			<td>
			<input type="text" class="caixa1" name="email" value="<%out.print(request.getAttribute("email")); %>">
			</td>
		</tr>
			
	</table>
	<input type="button" value="Salvar" class=botao onclick="validar()">
	</form>
</section>
<script type="text/javascript" src="scripts/validador.js"></script>
</body>
</html>