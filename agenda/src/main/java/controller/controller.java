package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = {"/main","/insert","/select","/update","/delete","/report"})
/**
 * Servlet implementation class controller
 */
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
		//dao.testeConexao();
		
		String action = request.getServletPath();
		System.out.println(action);
		
		if(action.equals("/main")) {
			contatos(request, response);
		}
		else if(action.equals("/insert")) {
			novoContato(request, response);
			
		}else if(action.equals("/select")) {
			listarContatos(request, response);
			
		}else if(action.equals("/update")) {
			editarContatos(request, response);
			
		}else if(action.equals("/delete")) {
			removerContato(request, response);
			
		}else if(action.equals("/report")) {
			gerarRelatorio(request, response);
			
		}else {
			response.sendRedirect("index.html");
		}
	}
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp");
		
		ArrayList<JavaBeans> lista = dao.listarContatos();
		
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.inserirContato(contato);
		
		response.sendRedirect("main");
	}
	protected void listarContatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idcon = request.getParameter("idcon");
		contato.setIdcon(idcon);
		dao.selecionarContato(contato);
		//System.out.println(contato.getIdcon());
		//System.out.println(contato.getNome());
		//System.out.println(contato.getFone());
		//System.out.println(contato.getEmail());
		
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
		
	}
	
	protected void editarContatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("a braba do jaca");
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.alterarContato(contato);
		response.sendRedirect("main");
	}
	
	protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idcon = request.getParameter("idcon");
		
		contato.setIdcon(idcon);
		dao.deletarContato(contato);
		response.sendRedirect("main");
	}
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document documento = new Document();
		try {
			response.setContentType("aplication/pdf");
			response.addHeader("Content-Disposition","inline; filename=" + "contato.pdf");
			
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			documento.open();
			documento.add(new Paragraph("lista de contatos"));
			documento.add(new Paragraph(" "));
			PdfPTable tabela = new PdfPTable(3);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			ArrayList<JavaBeans> lista = dao.listarContatos();
			
			for(int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			
			documento.close();
			
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
