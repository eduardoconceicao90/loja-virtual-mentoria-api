package com.eduardo.lojavirtual.model.dto.getResponse;

import java.util.ArrayList;

public class NewsLetterGetResponseDTO {

    /*Conteúdo do e-mail em html ou texto*/
    private ContentDTO content = new ContentDTO();;

    private ArrayList<String> flags = new ArrayList<String>();

    /*Nome do email no máximo 128 letras*/
    private String name;

    /*Tipo broadcast : transmissao ou draft : rascunho*/
    private String type = "broadcast";


    private String editor = "custom";
    /*O assunto do e-mail*/
    private String subject;

    /*email da pessoal que está enviado*/
    private FromFieldDTO fromField = new FromFieldDTO();

    /*email para endereco de resposta*/
    private ReplyToDTO replyTo = new ReplyToDTO();

    /*Campanha na qual o e-mail é atribuido*/
    private LeadCampanhaGetResponseCadastroDTO campaign = new LeadCampanhaGetResponseCadastroDTO();

    /*Data de envio 2022-05-12T18:20:52-03:00*/
    private String sendOn;

    /*Os anexos e arquivos caso queira enviar*/
    private ArrayList<AttachmenteNewsLatterGetResponseDTO> attachments = new ArrayList<>();

    /*Configuraçoes extras*/
    private SendSettingsDTO sendSettings = new SendSettingsDTO();

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }

    public ArrayList<String> getFlags() {
        return flags;
    }

    public void setFlags(ArrayList<String> flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public FromFieldDTO getFromField() {
        return fromField;
    }

    public void setFromField(FromFieldDTO fromField) {
        this.fromField = fromField;
    }

    public ReplyToDTO getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ReplyToDTO replyTo) {
        this.replyTo = replyTo;
    }

    public LeadCampanhaGetResponseCadastroDTO getCampaign() {
        return campaign;
    }

    public void setCampaign(LeadCampanhaGetResponseCadastroDTO campaign) {
        this.campaign = campaign;
    }

    public String getSendOn() {
        return sendOn;
    }

    public void setSendOn(String sendOn) {
        this.sendOn = sendOn;
    }

    public ArrayList<AttachmenteNewsLatterGetResponseDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<AttachmenteNewsLatterGetResponseDTO> attachments) {
        this.attachments = attachments;
    }

    public SendSettingsDTO getSendSettings() {
        return sendSettings;
    }

    public void setSendSettings(SendSettingsDTO sendSettings) {
        this.sendSettings = sendSettings;
    }
}
