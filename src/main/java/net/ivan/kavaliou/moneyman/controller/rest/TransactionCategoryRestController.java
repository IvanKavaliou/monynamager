package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.TransactionCategoryForm;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.service.TransactionCategoryService;
import net.ivan.kavaliou.moneyman.service.TransactionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/rest")
public class TransactionCategoryRestController {

    @Autowired
    TransactionCategoryService tCategoryService;

    @Autowired
    TransactionTypesService tTypeService;

    @PostMapping("/category/save")
    private TransactionCategoryForm add(@RequestBody @Valid TransactionCategoryForm form){
        log.debug("TransactionCategoryRestController::TransactionCategoryForm form={}"+form);
        TransactionCategory tc = TransactionCategory.builder()
                .id(form.getId())
                .name(form.getName())
                .transactionType(tTypeService.get(form.getTransactionType()))
                .build();
        form.setId(tCategoryService.save(tc).getId());
        return form;
    }

    @GetMapping("/category/delete/{id}")
    private boolean delete(@PathVariable Integer id){
        log.debug("TransactionCategoryRestController::delete id={}"+id);
            return tCategoryService.delete(id);
    }
}
