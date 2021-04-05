/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp_tp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

/**
 *
 * @author et007
 */
public class FindProcess {
     Snmp snmp = null;
    public void start() throws IOException {
         
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);

        transport.listen();
    }
    public ResponseEvent get(OID oids[]) throws IOException {
      
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("Time out/ Tempo atingido");
    }

    private Target getTarget() {
        Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("gr2020"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public String getAsString(OID oid) throws IOException {
        ResponseEvent event = get(new OID[]{oid});
        return event.getResponse().get(0).getVariable().toString();
    }
    
    
    public List<VariableBinding[]> processReturn(OID[] OIDsProcesses){
         List<VariableBinding[]> vbs = new ArrayList<>();
        try {
           
           // Protocolo usado: udp
        // Endereco do Agente: localhost = 127.0.0.1
        //  Port para o agente: 161
        Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
        CommunityTarget target = new CommunityTarget();
        // community string
        target.setCommunity(new OctetString("gr2020"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);

        // creating PDU factory
        DefaultPDUFactory pduFactory = new DefaultPDUFactory(PDU.GETBULK);
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen(); // open port to receive response

        // treeUtils allow us to get all the subtree of a MIB without having to manage the responses
        TreeUtils tree = new TreeUtils(snmp, pduFactory);
        tree.setMaxRepetitions(100); // 100 is the max

        // this walk uses GETBULK primitives to obtain all the instances
        // GETBULK can only handle 100 instances in the response (snmp4j)
        // normally we have around 300 process running in our machines
        // so the TreeUtils will run GETBULK 3 times in order to gives us the hole subtree of the mib
        List<TreeEvent> listWalk = tree.walk(target, OIDsProcesses);
        vbs = new ArrayList<>(listWalk.size()); // vbs is a list of arrays, therefore it can be seen as a matrix
        // each TreeEvent contains an array of VariableBinding which are the responses from the agent
        // as we ask for 3 different objects every, VaraibleBinding array will have an instance for each one of those objects

        int errorStatus = PDU.noError;
        for (TreeEvent treeEvent : listWalk) {
            errorStatus = treeEvent.getStatus();
            if (errorStatus == PDU.noError)  // check for errors
                vbs.add(treeEvent.getVariableBindings()); // copying the results to a data collection that can be manipulated later
            else
                System.out.println("error: " +errorStatus +"\n");
        }

        } catch (Exception e) {
        }
        
        return vbs;
    }
    
    
}
