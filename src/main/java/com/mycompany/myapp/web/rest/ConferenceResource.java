package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ConferenceRepository;
import com.mycompany.myapp.service.ConferenceService;
import com.mycompany.myapp.service.dto.ConferenceDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Conference}.
 */
@RestController
@RequestMapping("/api")
public class ConferenceResource {

    private final Logger log = LoggerFactory.getLogger(ConferenceResource.class);

    private static final String ENTITY_NAME = "msmfebundleConference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConferenceService conferenceService;

    private final ConferenceRepository conferenceRepository;

    public ConferenceResource(ConferenceService conferenceService, ConferenceRepository conferenceRepository) {
        this.conferenceService = conferenceService;
        this.conferenceRepository = conferenceRepository;
    }

    /**
     * {@code POST  /conferences} : Create a new conference.
     *
     * @param conferenceDTO the conferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conferenceDTO, or with status {@code 400 (Bad Request)} if the conference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conferences")
    public ResponseEntity<ConferenceDTO> createConference(@RequestBody ConferenceDTO conferenceDTO) throws URISyntaxException {
        log.debug("REST request to save Conference : {}", conferenceDTO);
        if (conferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new conference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConferenceDTO result = conferenceService.save(conferenceDTO);
        return ResponseEntity
            .created(new URI("/api/conferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conferences/:id} : Updates an existing conference.
     *
     * @param id the id of the conferenceDTO to save.
     * @param conferenceDTO the conferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conferenceDTO,
     * or with status {@code 400 (Bad Request)} if the conferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conferences/{id}")
    public ResponseEntity<ConferenceDTO> updateConference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConferenceDTO conferenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Conference : {}, {}", id, conferenceDTO);
        if (conferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConferenceDTO result = conferenceService.save(conferenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conferences/:id} : Partial updates given fields of an existing conference, field will ignore if it is null
     *
     * @param id the id of the conferenceDTO to save.
     * @param conferenceDTO the conferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conferenceDTO,
     * or with status {@code 400 (Bad Request)} if the conferenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conferenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conferences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConferenceDTO> partialUpdateConference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConferenceDTO conferenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conference partially : {}, {}", id, conferenceDTO);
        if (conferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConferenceDTO> result = conferenceService.partialUpdate(conferenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conferenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conferences} : get all the conferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conferences in body.
     */
    @GetMapping("/conferences")
    public List<ConferenceDTO> getAllConferences() {
        log.debug("REST request to get all Conferences");
        return conferenceService.findAll();
    }

    /**
     * {@code GET  /conferences/:id} : get the "id" conference.
     *
     * @param id the id of the conferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conferences/{id}")
    public ResponseEntity<ConferenceDTO> getConference(@PathVariable Long id) {
        log.debug("REST request to get Conference : {}", id);
        Optional<ConferenceDTO> conferenceDTO = conferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conferenceDTO);
    }

    /**
     * {@code DELETE  /conferences/:id} : delete the "id" conference.
     *
     * @param id the id of the conferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conferences/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        log.debug("REST request to delete Conference : {}", id);
        conferenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
