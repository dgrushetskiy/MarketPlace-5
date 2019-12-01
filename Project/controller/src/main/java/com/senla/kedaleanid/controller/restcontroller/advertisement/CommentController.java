package com.senla.kedaleanid.controller.restcontroller.advertisement;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.CommentDto;
import com.senla.kedaleanid.serviceapi.model.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@RestController
@RequestMapping(value = "/ads/{id}/comments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CommentController extends AbstractController {

    private ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDto> getAdComments(@PathVariable(value = "id") int id,
                                          @RequestParam(value = "fc", required = false) Integer firstComment,
                                          @RequestParam(value = "sc", required = false) Integer commentPageSize) {
        Pagination pagination = configurePagination(firstComment, commentPageSize);
        List<CommentDto> commentDtoList = castModelDtoList(commentService.getAdComments(id, pagination.getFirstElement(),
                pagination.getPageSize(), CommentDto.class),
                CommentDto.class);

        if (commentDtoList == null || commentDtoList.isEmpty()) {
            commentDtoList = new ArrayList<>();
            commentDtoList.add(new CommentDto());
        }
        return commentDtoList;
    }

    @PostMapping(value = "/leave-comment", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean leaveComment(@RequestBody @Validated(value = CommentDto.LeaveCommentGroup.class) CommentDto object) {
        checkAuthority(object.getOwner().getId());
        return commentService.create(object);
    }

    @PutMapping(value = "/edit-comment", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean editComment(@RequestBody @Validated(value = CommentDto.EditCommentGroup.class) CommentDto object) {
        checkAuthority(object.getOwner().getId());
        return commentService.update(object);
    }

    @DeleteMapping(value = "/delete-comment", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean deleteChat(@RequestBody @Validated(value = CommentDto.DeleteCommentGroup.class) CommentDto object) {
        checkAuthority(object.getOwner().getId());
        return commentService.delete(object);
    }
}
