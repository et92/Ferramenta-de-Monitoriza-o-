/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp_tp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import writeFile.ReadFromFile2;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LerConsole {

	public static void main(String[] args) {

		try {
                   int opcaoMenu;
                    String linhaLida =null;
                   
                    System.out.println("1 - Imprimir todos os processos\n");
                    System.out.println("2 - Imprimir apenas um processo\n");
                    
                    System.out.println("Escolha uma opcao: \n");
                    BufferedReader menu = new BufferedReader(new InputStreamReader(System.in));

                    opcaoMenu = Integer.parseInt(menu.readLine());
                    
                    ReadFromFile2 arquivoDiretorio = new ReadFromFile2();
                    BufferedReader arquivoLido = arquivoDiretorio.arquivoLido();
                    
                    switch(opcaoMenu){
                        case 1:
                            linhaLida = arquivoLido.readLine();
                            while (linhaLida != null) {
				System.out.printf("%s\n", linhaLida);
                                System.out.printf("%s\n.", arquivoLido.readLine());
				linhaLida = arquivoLido.readLine();
                            }
                            break;
                            
                        case 2:
                             System.out.println("Digite o numero do processo: \n");

                            BufferedReader valorDigitadoMemoria = new BufferedReader(new InputStreamReader(System.in));
                            String numeroProcesso = "Process ID: " + valorDigitadoMemoria.readLine();

                            linhaLida = arquivoLido.readLine();

                            int cont = 3;
                            while (linhaLida != null) {
                                    if (numeroProcesso.equals(linhaLida)) {
                                            System.out.printf("%s\n", linhaLida);

                                            for (int i = 0; i < cont; i++) {
                                                    System.out.printf("%s\n.", arquivoLido.readLine());
                                            }
                                    }
                                    linhaLida = arquivoLido.readLine();
                            }
                            break;
                            
                            default:
                                System.out.println("Ups Wrong!Run again and Select 1 ou 2 \n");
                            break;
                    }
                   
                    } catch (Exception e) {
			e.printStackTrace();
		}
        }
}
