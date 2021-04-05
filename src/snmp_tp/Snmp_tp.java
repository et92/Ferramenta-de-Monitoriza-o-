

package snmp_tp;

import java.util.List;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import writeFile.WriteFile;




/**
 * @author et007
 */
public class Snmp_tp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FindProcess processReturn = new FindProcess();
            StringBuffer buffer = new StringBuffer();
            
             // OID of MIB RFC 1213; Scalar Object = .iso.org.dod.internet.mgmt.mib-2
            OID[] OIDsProcesses = {
                        new OID(new int[] {1,3,6,1,2,1,25,4,2,1,2}), 
            // mib-2.host.hrSWRun.hrSWRunTable.hrSWRunEntry.hrSWRunName (process names)
                        new OID(new int[] {1,3,6,1,2,1,25,5,1,1,1}), 
            // mib-2.host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfCPU (process CPU time)
                        new OID(new int[] {1,3,6,1,2,1,25,5,1,1,2})  
            // mib-2.host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfMem (process allocated mem)
                        };
            
            OID[] OIDsProcessorLoad = {
                        new OID(new int[] {1,3,6,1,2,1,25,3,3,1,2}), 
            // mib-2.host.hrDevice.hrProcessorTable.hrProcessorEntry.hrProcessorLoad (Processor load by thread)
                        };
     
            OID[] OIDsVars = {
                        new OID(new int[] {1,3,6,1,2,1,25,1,6,0}), 
            // mib-2.host.hrSystem.hrSystemProcesses (total number of processes)
                        new OID(new int[] {1,3,6,1,2,1,25,2,2,0}), 
            // mib-2.host.hrStorage.hrMemorySize (total memory size)
                        };

            //Process Type  
           String processOne = "OIDsProcesses";
           String processTwo = "OIDsProcessorLoad";
           String processThree = "OIDsVars";

           if(processOne.contains("OIDsProcesses")){
               
               buffer.append("------------------------------------------------------").append("\n");
               buffer.append("|Process names/ Process CPU time/ Process allocated mem|").append("\n");
               buffer.append("------------------------------------------------------").append("\n");
                List<VariableBinding[]> vbs = processReturn.processReturn(OIDsProcesses);
                // print
                int counter = 0;
                int nProc = 0; // for counting the number of processes
                for(VariableBinding[] vba : vbs) {
                    for(VariableBinding vb : vba) {
                        if(counter % OIDsProcesses.length == 0) {
                           buffer.append("Process ID: ").append(vb.getOid().last()).append("\n");
                            nProc++;
                        }
                        counter++;
                        buffer.append(vb.toString()).append("\n");
                    }
                }
                buffer.append("Number of processes calculated: ").append(nProc).append("\n");
                buffer.append("\n");
                buffer.append("\n");
           }

           if(processTwo.contains("OIDsProcessorLoad")){
               buffer.append("------------------------------------------------------").append("\n");
               buffer.append("|              Processor load by thread              |").append("\n");
               buffer.append("------------------------------------------------------").append("\n");
                List<VariableBinding[]> vbs = processReturn.processReturn(OIDsProcessorLoad);
                // print
                int counter = 0;
                int nProc = 0; // for counting the number of processes
                for(VariableBinding[] vba : vbs) {
                    for(VariableBinding vb : vba) {
                        if(counter % OIDsProcesses.length == 0) {
                             buffer.append("Process ID: ").append(vb.getOid().last()).append("\n");
                            nProc++;
                        }
                        counter++;
                         buffer.append(vb.toString()).append("\n");
                    }
                }
                buffer.append("Number of processes calculated: ").append(nProc).append("\n");
                buffer.append("\n");
                buffer.append("\n");
           }

            if(processTwo.contains("OIDsProcessorLoad")){
               buffer.append("------------------------------------------------------").append("\n");
               buffer.append("| Total number of processes/ Total memory size              |").append("\n");
               buffer.append("------------------------------------------------------").append("\n");
                
                processReturn.start();
                String memory = processReturn.getAsString(new OID("1.3.6.1.2.1.25.2.2.0"));
                
                buffer.append("Total Memory: ").append(memory).append("\n");
                buffer.append("Number of processes calculated: ").append(1).append("\n");
                buffer.append("\n");
                buffer.append("\n");
            }
            
            
            WriteFile write = new WriteFile();
            write.writing(buffer.toString());
            
                
 
        } catch (Exception e) {
        }
            
    }
    
}
