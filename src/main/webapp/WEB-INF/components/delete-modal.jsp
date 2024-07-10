<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<div class="modal fade bd-example-modal-lg" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="delete-modal-title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="header-modal">
                <h5 class="header-modal-title" id="delete-modal-title">CONFIRMAR EXCLUSÃO</h5>
            </div>
            <div class="body-modal">
                <h6 class="deleteWarning">
                    Deseja mesmo excluir todos os dados do item atual? (Essa mudança é irreversível)
                </h6>
                <div class="cancel-delete-buttons">
                    <a data-dismiss="modal" id="cancelButton">CANCELAR</a>
                    <a href="" id="deleteButton">
                        EXCLUIR
                        <svg width="19" height="20" viewBox="0 0 19 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="material-symbols:delete" clip-path="url(#clip0_760_2829)">
                                <path id="Vector" d="M3.88379 18.6316C3.29941 18.6316 2.79933 18.4359 2.38354 18.0446C1.96775 17.6533 1.7595 17.1823 1.75879 16.6316V3.63159H0.696289V1.63159H6.00879V0.631592H12.3838V1.63159H17.6963V3.63159H16.6338V16.6316C16.6338 17.1816 16.4259 17.6526 16.0101 18.0446C15.5943 18.4366 15.0939 18.6323 14.5088 18.6316H3.88379ZM6.00879 14.6316H8.13379V5.63159H6.00879V14.6316ZM10.2588 14.6316H12.3838V5.63159H10.2588V14.6316Z" fill="#F3F3F3"></path>
                            </g>
                            <defs>
                                <clipPath id="clip0_760_2829">
                                    <rect width="18" height="19" fill="white" transform="translate(0.5 0.5)"></rect>
                                </clipPath>
                            </defs>
                        </svg>

                    </a>
                </div>
            </div>
            <div class="footer-modal"></div>
        </div>
    </div>
</div>