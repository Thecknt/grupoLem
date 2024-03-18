
package grupoLem.appGestion.controller;

import grupoLem.appGestion.model.MediaPayment;
import grupoLem.appGestion.service.MediaPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class MediaPaymentController {
    @Autowired
    private MediaPaymentService mediaPaymentsService;

    @GetMapping("/mediosDePago")
    public List<MediaPayment> getAllMediaPayment() {
        List<MediaPayment> allMedia = this.mediaPaymentsService.getAllMediaPayments();
        return allMedia;
    }
}

