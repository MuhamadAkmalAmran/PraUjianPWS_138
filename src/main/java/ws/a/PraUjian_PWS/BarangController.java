/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.a.PraUjian_PWS;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.a.PraUjian_PWS.exceptions.PreexistingEntityException;

/**
 *
 * @author akmal
 */
@Controller
@ResponseBody
public class BarangController {

    Barang data = new Barang();
    BarangJpaController BarangJpa = new BarangJpaController();

    @RequestMapping("/getName/{id}")
    public String getName(@PathVariable("id") int id) {
        try {
            data = BarangJpa.findBarang(id);
            return data.getName() + "<br>" + data.getJumlah();
        } catch (Exception e) {
            return "Data Not Found";
        }
    }

    @RequestMapping("/getAll")
    public List<Barang> viewAll() {
        return BarangJpa.findBarangEntities();
    }

    @RequestMapping("/deleteName/{id}")
    public String deleteName(@PathVariable("id") int id) {
        try {
            BarangJpa.destroy(id);
            return "Data" + id + "has been deteled";

        } catch (Exception e) {
            return id + "Data Not Found";
        }
    }
}
