import { toast } from "sonner";
import { LoginSchema } from "../schemas/login-schema";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import { axiosInstance } from "@/lib/axios-config";
import { useRouter } from "@tanstack/react-router";

export function useLogin() {
  const queryClient = useQueryClient();
  const router = useRouter();

  return useMutation({
    mutationFn: async (values: LoginSchema) => {
      console.log("Sending data to backend:", values);
      const resp = await axiosInstance.post("/login", values);
      return resp.data;
    },
    onSuccess: (data) => {
      const { token } = data;
      localStorage.setItem("jwtToken", token);

      queryClient.invalidateQueries({
        queryKey: ["auth"],
      });
      toast.success("Logged in successfully.");
      router.navigate({ to: "/dashboard" });
    },
    onError: () => {
      toast.error("Failed to login.");
    },
  });
}
